fun goo() {
    var a = 2
    val b = 4
    a <!CAN_BE_REPLACED_WITH_OPERATOR_ASSIGNMENT!>=<!> a + 1 + b
    a <!CAN_BE_REPLACED_WITH_OPERATOR_ASSIGNMENT!>=<!> (a + 1)
    a = a * b + 1
}
