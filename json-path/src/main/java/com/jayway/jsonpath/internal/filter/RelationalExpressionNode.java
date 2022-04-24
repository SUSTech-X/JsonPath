package com.jayway.jsonpath.internal.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create an node represent a expression
 * You can create a RelationalExpressionNode by using public constructor
 */
public class RelationalExpressionNode extends ExpressionNode {

    private static final Logger logger = LoggerFactory.getLogger(RelationalExpressionNode.class);

    private final ValueNode left;
    private final RelationalOperator relationalOperator;
    private final ValueNode right;

    /**
     * To found a node which contains an expression.
     *
     * @param left               the left node
     * @param relationalOperator the relationalOperator, for example "=="
     * @param right              the right node
     */
    //CS304 Issue link: https://github.com/json-path/JsonPath/issues/771
    public RelationalExpressionNode(ValueNode left, RelationalOperator relationalOperator, ValueNode right) {
        try {
            // if need change left and right, change them
            if (right.toString().charAt(0) == '@') { //NOPMD - suppressed AvoidLiteralsInIfCondition
                final ValueNode tmp = left;
                left = right; //NOPMD - suppressed AvoidReassigningParameters
                right = tmp; //NOPMD - suppressed AvoidReassigningParameters
                relationalOperator = reverse(relationalOperator); //NOPMD - suppressed AvoidReassigningParameters
            }
        } catch (Exception e) { //NOPMD - suppressed AvoidCatchingGenericException
            // if can't change, restore them
            final ValueNode tmp = left;
            left = right;
            right = tmp;
        }
        this.left = left;
        this.relationalOperator = relationalOperator;
        this.right = right;

        logger.trace("ExpressionNode {}", toString());
    }

    @Override
    public String toString() {
        if (relationalOperator == RelationalOperator.EXISTS) {
            return left.toString();
        } else {
            return left.toString() + " " + relationalOperator.toString() + " " + right.toString();
        }
    }

    @Override
    public boolean apply(PredicateContext ctx) {
        ValueNode l = left;
        ValueNode r = right;

        if (left.isPathNode()) {
            l = left.asPathNode().evaluate(ctx);
        }
        if (right.isPathNode()) {
            r = right.asPathNode().evaluate(ctx);
        }
        Evaluator evaluator = EvaluatorFactory.createEvaluator(relationalOperator);
        if (evaluator != null) {
            return evaluator.evaluate(l, r, ctx);
        }
        return false;
    }


    /**
     * To inverse an operator, if can't, throw an exception
     *
     * @param operator the operator need to be inversed
     * @return the inverse operator
     * @throws Exception the operator can't be inversed
     */
    //CS304 Issue link: https://github.com/json-path/JsonPath/issues/771 //NOPMD - suppressed SignatureDeclareThrowsException
    private RelationalOperator reverse(final RelationalOperator operator)throws Exception { //NOPMD - suppressed SignatureDeclareThrowsException
        switch (operator) {
            case EQ:
                return RelationalOperator.EQ; //NOPMD - suppressed OnlyOneReturn
            case GTE:
                return RelationalOperator.LTE; //NOPMD - suppressed OnlyOneReturn
            case LTE:
                return RelationalOperator.GTE; //NOPMD - suppressed OnlyOneReturn
            case GT:
                return RelationalOperator.LT; //NOPMD - suppressed OnlyOneReturn
            case NE:
                return RelationalOperator.NE;
            default: //NOPMD - suppressed AvoidThrowingRawExceptionTypes
                throw new Exception("Type can't change!"); //NOPMD - suppressed AvoidThrowingRawExceptionTypes
        }
    }
}