// RUN_PIPELINE_TILL: SOURCE
// FIR_IDENTICAL
import java.util.HashMap

private fun <A> unaryOperation(
        a: CompileTimeType<A>,
        functionName: String,
        operation: Function1<A, Any>,
        checker: Function1<Long, Long>
) = UnaryOperationKey(a, functionName) to Pair(operation, checker) <!UNCHECKED_CAST!>as Pair<Function1<Any?, Any>, Function1<Long, Long>><!>

private fun <A, B> binaryOperation(
        a: CompileTimeType<A>,
        b: CompileTimeType<B>,
        functionName: String,
        operation: Function2<A, B, Any>,
        checker: Function2<BigInteger, BigInteger, BigInteger>
) = BinaryOperationKey(a, b, functionName) to Pair(operation, checker) <!UNCHECKED_CAST!>as Pair<Function2<Any?, Any?, Any>, Function2<BigInteger, BigInteger, BigInteger>><!>

private data class UnaryOperationKey<A>(val f: CompileTimeType<out A>, val functionName: String)
//HashMap<BinaryOperationKey<*, *>, Pair<Function2<Any?, Any?, Any>, Function2<BigInteger, BigInteger, BigInteger>>>
private data class BinaryOperationKey<A, B>(val f: CompileTimeType<out A>, val g: CompileTimeType<out B>, val functionName: String)

private class CompileTimeType<T>

private val BYTE = CompileTimeType<Byte>()
private val CHAR = CompileTimeType<Char>()
private val BOOLEAN = CompileTimeType<Boolean>()
private val DOUBLE = CompileTimeType<Double>()
private val FLOAT = CompileTimeType<Float>()
private val INT = CompileTimeType<Int>()
private val LONG = CompileTimeType<Long>()
private val SHORT = CompileTimeType<Short>()
private val STRING = CompileTimeType<String>()
private val ANY = CompileTimeType<Any>()


private val emptyBinaryFun: Function2<BigInteger, BigInteger, BigInteger> = { a, b -> BigInteger("0") }
private val emptyUnaryFun: Function1<Long, Long> = { a -> 1.toLong() }

private val unaryOperations: HashMap<UnaryOperationKey<*>, Pair<Function1<Any?, Any>, Function1<Long, Long>>>
        = hashMapOf<UnaryOperationKey<*>, Pair<Function1<Any?, Any>, Function1<Long, Long>>>(
        unaryOperation(BOOLEAN, "not!", { a -> a.not() }, emptyUnaryFun),
        unaryOperation(BYTE, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(BYTE, "minus", { a -> a.unaryMinus() }, { a -> a.unaryMinus() }),
        unaryOperation(BYTE, "minus", { a -> a.unaryMinus() }, { a -> a.unaryMinus() }),
        unaryOperation(BYTE, "toChar", { a -> a.<!DEPRECATION!>toChar<!>() }, emptyUnaryFun),
        unaryOperation(BYTE, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(BYTE, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(BYTE, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(BYTE, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(BYTE, "toShort", { a -> a.toShort() }, emptyUnaryFun),
        unaryOperation(BYTE, "toByte", { a -> a.toByte() }, emptyUnaryFun),
        unaryOperation(CHAR, "toInt", { a -> a.<!DEPRECATION!>toInt<!>() }, emptyUnaryFun),
        unaryOperation(CHAR, "toChar", { a -> a.toChar() }, emptyUnaryFun),
        unaryOperation(CHAR, "toLong", { a -> a.<!DEPRECATION!>toLong<!>() }, emptyUnaryFun),
        unaryOperation(CHAR, "toFloat", { a -> a.<!DEPRECATION!>toFloat<!>() }, emptyUnaryFun),
        unaryOperation(CHAR, "toDouble", { a -> a.<!DEPRECATION!>toDouble<!>() }, emptyUnaryFun),
        unaryOperation(CHAR, "toShort", { a -> a.<!DEPRECATION!>toShort<!>() }, emptyUnaryFun),
        unaryOperation(CHAR, "toByte", { a -> a.<!DEPRECATION!>toByte<!>() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "minus", { a -> a.unaryMinus() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toChar", { a -> a.<!DEPRECATION!>toChar<!>() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toShort", { a -> a.<!DEPRECATION_ERROR!>toShort<!>() }, emptyUnaryFun),
        unaryOperation(DOUBLE, "toByte", { a -> a.<!DEPRECATION_ERROR!>toByte<!>() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(FLOAT, "minus", { a -> a.unaryMinus() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toChar", { a -> a.<!DEPRECATION!>toChar<!>() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(FLOAT, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toShort", { a -> a.<!DEPRECATION_ERROR!>toShort<!>() }, emptyUnaryFun),
        unaryOperation(FLOAT, "toByte", { a -> a.<!DEPRECATION_ERROR!>toByte<!>() }, emptyUnaryFun),
        unaryOperation(INT, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(INT, "toShort", { a -> a.toShort() }, emptyUnaryFun),
        unaryOperation(INT, "toByte", { a -> a.toByte() }, emptyUnaryFun),
        unaryOperation(INT, "inv", { a -> a.inv() }, emptyUnaryFun),
        unaryOperation(INT, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(INT, "minus", { a -> a.unaryMinus() }, { a -> a.unaryMinus() }),
        unaryOperation(INT, "toChar", { a -> a.toChar() }, emptyUnaryFun),
        unaryOperation(INT, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(INT, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(INT, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(LONG, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(LONG, "toShort", { a -> a.toShort() }, emptyUnaryFun),
        unaryOperation(LONG, "toByte", { a -> a.toByte() }, emptyUnaryFun),
        unaryOperation(LONG, "inv", { a -> a.inv() }, emptyUnaryFun),
        unaryOperation(LONG, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(LONG, "minus", { a -> a.unaryMinus() }, { a -> a.unaryMinus() }),
        unaryOperation(LONG, "toChar", { a -> a.<!DEPRECATION!>toChar<!>() }, emptyUnaryFun),
        unaryOperation(LONG, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(LONG, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(LONG, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(SHORT, "toInt", { a -> a.toInt() }, emptyUnaryFun),
        unaryOperation(SHORT, "minus", { a -> a.unaryMinus() }, { a -> a.unaryMinus() }),
        unaryOperation(SHORT, "toChar", { a -> a.<!DEPRECATION!>toChar<!>() }, emptyUnaryFun),
        unaryOperation(SHORT, "toLong", { a -> a.toLong() }, emptyUnaryFun),
        unaryOperation(SHORT, "plus", { a -> a.unaryPlus() }, emptyUnaryFun),
        unaryOperation(SHORT, "toFloat", { a -> a.toFloat() }, emptyUnaryFun),
        unaryOperation(SHORT, "toDouble", { a -> a.toDouble() }, emptyUnaryFun),
        unaryOperation(SHORT, "toShort", { a -> a.toShort() }, emptyUnaryFun),
        unaryOperation(SHORT, "toByte", { a -> a.toByte() }, emptyUnaryFun),
        unaryOperation(STRING, "toString", { a -> a.toString() }, emptyUnaryFun)
)

private val binaryOperations: HashMap<BinaryOperationKey<*, *>, Pair<Function2<Any?, Any?, Any>, Function2<BigInteger, BigInteger, BigInteger>>>
        = hashMapOf<BinaryOperationKey<*, *>, Pair<Function2<Any?, Any?, Any>, Function2<BigInteger, BigInteger, BigInteger>>>(
        binaryOperation(BOOLEAN, BOOLEAN, "xor", { a, b -> a.xor(b) }, emptyBinaryFun),
        binaryOperation(BOOLEAN, BOOLEAN, "or", { a, b -> a.or(b) }, emptyBinaryFun),
        binaryOperation(BOOLEAN, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(BOOLEAN, BOOLEAN, "and", { a, b -> a.and(b) }, emptyBinaryFun),
        binaryOperation(BOOLEAN, BOOLEAN, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, BYTE, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(BYTE, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(BYTE, LONG, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(BYTE, SHORT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(BYTE, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(BYTE, BYTE, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(BYTE, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(BYTE, LONG, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(BYTE, SHORT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(BYTE, BYTE, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(BYTE, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(BYTE, LONG, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(BYTE, SHORT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(BYTE, BYTE, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(BYTE, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(BYTE, LONG, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(BYTE, SHORT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(BYTE, BYTE, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(BYTE, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(BYTE, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(BYTE, INT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(BYTE, LONG, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(BYTE, SHORT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(BYTE, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(CHAR, CHAR, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(CHAR, CHAR, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(CHAR, INT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(CHAR, INT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(CHAR, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, BYTE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, INT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, LONG, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, SHORT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(DOUBLE, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, BYTE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, INT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, LONG, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, SHORT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(FLOAT, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(INT, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(INT, BYTE, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(INT, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(INT, LONG, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(INT, SHORT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(INT, INT, "shl", { a, b -> a.shl(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "ushr", { a, b -> a.ushr(b) }, emptyBinaryFun),
        binaryOperation(INT, BYTE, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(INT, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(INT, LONG, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(INT, SHORT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(INT, INT, "shr", { a, b -> a.shr(b) }, emptyBinaryFun),
        binaryOperation(INT, BYTE, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(INT, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(INT, LONG, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(INT, SHORT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(INT, BYTE, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(INT, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(INT, LONG, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(INT, SHORT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(INT, INT, "or", { a, b -> a.or(b) }, { a, b -> a.or(b) }),
        binaryOperation(INT, BYTE, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(INT, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(INT, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(INT, INT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(INT, LONG, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(INT, SHORT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(INT, INT, "and", { a, b -> a.and(b) }, { a, b -> a.and(b) }),
        binaryOperation(INT, INT, "xor", { a, b -> a.xor(b) }, { a, b -> a.xor(b) }),
        binaryOperation(INT, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(LONG, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(LONG, BYTE, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(LONG, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(LONG, LONG, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(LONG, SHORT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(LONG, INT, "shl", { a, b -> a.shl(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "ushr", { a, b -> a.ushr(b) }, emptyBinaryFun),
        binaryOperation(LONG, BYTE, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(LONG, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(LONG, LONG, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(LONG, SHORT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(LONG, INT, "shr", { a, b -> a.shr(b) }, emptyBinaryFun),
        binaryOperation(LONG, BYTE, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(LONG, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(LONG, LONG, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(LONG, SHORT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(LONG, BYTE, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(LONG, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(LONG, LONG, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(LONG, SHORT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(LONG, LONG, "or", { a, b -> a.or(b) }, { a, b -> a.or(b) }),
        binaryOperation(LONG, BYTE, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(LONG, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(LONG, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(LONG, INT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(LONG, LONG, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(LONG, SHORT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(LONG, LONG, "and", { a, b -> a.and(b) }, { a, b -> a.and(b) }),
        binaryOperation(LONG, LONG, "xor", { a, b -> a.xor(b) }, { a, b -> a.xor(b) }),
        binaryOperation(LONG, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(SHORT, BYTE, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(SHORT, DOUBLE, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "minus", { a, b -> a.minus(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(SHORT, LONG, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(SHORT, SHORT, "minus", { a, b -> a.minus(b) }, { a, b -> a.subtract(b) }),
        binaryOperation(SHORT, BYTE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, DOUBLE, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, LONG, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, SHORT, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun),
        binaryOperation(SHORT, BYTE, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(SHORT, DOUBLE, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(SHORT, LONG, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(SHORT, SHORT, "plus", { a, b -> a.plus(b) }, { a, b -> a.add(b) }),
        binaryOperation(SHORT, BYTE, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(SHORT, DOUBLE, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "div", { a, b -> a.div(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(SHORT, LONG, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(SHORT, SHORT, "div", { a, b -> a.div(b) }, { a, b -> a.divide(b) }),
        binaryOperation(SHORT, BYTE, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(SHORT, DOUBLE, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "rem", { a, b -> a.rem(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(SHORT, LONG, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(SHORT, SHORT, "rem", { a, b -> a.rem(b) }, { a, b -> a.rem(b) }),
        binaryOperation(SHORT, BYTE, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(SHORT, DOUBLE, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(SHORT, FLOAT, "times", { a, b -> a.times(b) }, emptyBinaryFun),
        binaryOperation(SHORT, INT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(SHORT, LONG, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(SHORT, SHORT, "times", { a, b -> a.times(b) }, { a, b -> a.multiply(b) }),
        binaryOperation(SHORT, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(STRING, ANY, "plus", { a, b -> a.plus(b) }, emptyBinaryFun),
        binaryOperation(STRING, INT, "get", { a, b -> a.get(b) }, emptyBinaryFun),
        binaryOperation(STRING, ANY, "equals", { a, b -> a.equals(b) }, emptyBinaryFun),
        binaryOperation(STRING, STRING, "compareTo", { a, b -> a.compareTo(b) }, emptyBinaryFun)
)

//from library
class BigInteger(val value: String) {
    fun add(o: BigInteger): BigInteger = o
    fun divide(o: BigInteger): BigInteger = o
    fun rem(o: BigInteger): BigInteger = o
    fun multiply(o: BigInteger): BigInteger = o
    fun subtract(o: BigInteger): BigInteger = o
    fun or(o: BigInteger): BigInteger = o
    fun and(o: BigInteger): BigInteger = o
    fun xor(o: BigInteger): BigInteger = o
}
