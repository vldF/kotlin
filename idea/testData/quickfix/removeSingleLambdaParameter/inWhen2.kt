// "Remove single lambda parameter declaration" "true"
fun test(i: Int) {
    val p: (String) -> Boolean =
        when (i) {
            1 -> { { <caret>s -> true } }
            else -> { s -> false }
        }
}