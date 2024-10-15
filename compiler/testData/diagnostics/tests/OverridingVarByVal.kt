// RUN_PIPELINE_TILL: SOURCE
// FIR_IDENTICAL
// RENDER_DIAGNOSTICS_FULL_TEXT
open class Var() {
  open var v : Int = 1
}

interface VarT {
  var v : Int
}

class Val() : Var(), VarT {
  override <!VAR_OVERRIDDEN_BY_VAL!>val<!> v : Int = 1
}

class Var2() : Var() {
  override var v : Int = 1
}
