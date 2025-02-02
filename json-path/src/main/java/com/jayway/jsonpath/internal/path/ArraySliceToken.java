/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayway.jsonpath.internal.path;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.internal.PathRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Add a method sliceAsArray. This is used in FILTER_SLICE_AS_ARRAY mode.
 */
public class ArraySliceToken extends ArrayPathToken {

    private static final Logger logger = LoggerFactory.getLogger(ArraySliceToken.class);

    private final ArraySliceOperation operation;

    ArraySliceToken(final ArraySliceOperation operation) {
        this.operation = operation;
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        if (!checkArrayModel(currentPath, model, ctx))
            return;
        switch (operation.operation()) {
            case SLICE_FROM:
                sliceFrom(currentPath, parent, model, ctx);
                break;
            case SLICE_BETWEEN:
                sliceBetween(currentPath, parent, model, ctx);
                break;
            case SLICE_TO:
                sliceTo(currentPath, parent, model, ctx);
                break;
        }
    }

    /**
     * Add a judgement, check whether using FILTER_SLICE_AS_ARRAY mode.
     * <p>
     *     Details about FILTER_SLICE_AS_ARRAY in com/jayway/jsonpath/Option.java
     * </p>
     *
     * @param currentPath current json path
     * @param parent path set operation reference
     * @param model current json model
     * @param ctx evaluation context in the evaluation
     */
    private void sliceFrom(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        int length = ctx.jsonProvider().length(model);
        int from = operation.from();
        if (from < 0) {
            //calculate slice start from array length
            from = length + from;
        }
        from = Math.max(0, from);

        logger.debug("Slice from index on array with length: {}. From index: {} to: {}. Input: {}", length, from, length - 1, toString());

        if (length == 0 || from >= length) {
            return;
        }

        //CS304 Issue link: https://github.com/json-path/JsonPath/issues/806
        if (ctx.configuration().containsOption(Option.FILTER_SLICE_AS_ARRAY)) {
            //using FILTER_SLICE_AS_ARRAY mode, details at com/jayway/jsonpath/Option.FILTER_SLICE_AS_ARRAY
            sliceAsArray(currentPath, model, ctx, from, length);
        } else {
            for (int i = from; i < length; i++) {
                handleArrayIndex(i, currentPath, model, ctx);
            }
        }
    }

    /**
     * Add a judgement, check whether using FILTER_SLICE_AS_ARRAY mode.
     * <p>
     *     Details about FILTER_SLICE_AS_ARRAY in com/jayway/jsonpath/Option.java
     * </p>
     *
     * @param currentPath current json path
     * @param parent path set operation reference
     * @param model current json model
     * @param ctx evaluation context in the evaluation
     */
    private void sliceBetween(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        int length = ctx.jsonProvider().length(model);
        int from = operation.from();
        int to = operation.to();

        to = Math.min(length, to);

        if (from >= to || length == 0) {
            return;
        }

        logger.debug("Slice between indexes on array with length: {}. From index: {} to: {}. Input: {}", length, from, to, toString());

        //CS304 Issue link: https://github.com/json-path/JsonPath/issues/806
        if (ctx.configuration().containsOption(Option.FILTER_SLICE_AS_ARRAY)) {
            //using FILTER_SLICE_AS_ARRAY mode, details at com/jayway/jsonpath/Option.FILTER_SLICE_AS_ARRAY
            sliceAsArray(currentPath, model, ctx, from, to);
        } else {
            for (int i = from; i < to; i++) {
                handleArrayIndex(i, currentPath, model, ctx);
            }
        }
    }

    /**
     * Add a judgement, check whether using FILTER_SLICE_AS_ARRAY mode.
     * <p>
     *     Details about FILTER_SLICE_AS_ARRAY in com/jayway/jsonpath/Option.java
     * </p>
     *
     * @param currentPath current json path
     * @param parent path set operation reference
     * @param model current json model
     * @param ctx evaluation context in the evaluation
     */
    private void sliceTo(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        int length = ctx.jsonProvider().length(model);
        if (length == 0) {
            return;
        }
        int to = operation.to();
        if (to < 0) {
            //calculate slice end from array length
            to = length + to;
        }
        to = Math.min(length, to);

        logger.debug("Slice to index on array with length: {}. From index: 0 to: {}. Input: {}", length, to, toString());

        //CS304 Issue link: https://github.com/json-path/JsonPath/issues/806
        if (ctx.configuration().containsOption(Option.FILTER_SLICE_AS_ARRAY)) {
            //using FILTER_SLICE_AS_ARRAY mode, details at com/jayway/jsonpath/Option.FILTER_SLICE_AS_ARRAY
            sliceAsArray(currentPath, model, ctx, 0, to);
        } else {
            for (int i = 0; i < to; i++) {
                handleArrayIndex(i, currentPath, model, ctx);
            }
        }
    }

    /**
     * Treat the array after slice as a whole json model.
     * <p>
     *      the method is used then using FILTER_SLICE_AS_ARRAY.
     *      Details about FILTER_SLICE_AS_ARRAY in com/jayway/jsonpath/Option.java
     * </p>
     *
     * @param currentPath current json path
     * @param model current json model
     * @param ctx evaluation context in the evaluation
     * @param from index from
     * @param to index to
     */
    //CS304 Issue link: https://github.com/json-path/JsonPath/issues/806
    private void sliceAsArray(final String currentPath, final Object model, final EvaluationContextImpl ctx, final int from, final int to) {
        final Object array = ctx.jsonProvider().createArray(); // create an array to store the element after filtered
        for (int i = from; i < to; i++) {
            final Object currentObject = ctx.jsonProvider().getArrayIndex(model, i);
            ((List) array).add(currentObject);
        }

        handleWholeArray(currentPath, array, ctx); // handle the whole array
    }

    @Override
    public String getPathFragment() {
        return operation.toString();
    }

    @Override
    public boolean isTokenDefinite() {
        return false;
    }

}
