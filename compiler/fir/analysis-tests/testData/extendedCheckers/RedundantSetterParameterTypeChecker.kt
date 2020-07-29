
@Target(AnnotationTarget.CLASS)
annotation class Ann

var x: Int
    get() = 1
    set(@Ann private x) { }


var x: String = ""
    set(param: <!REDUNDANT_SETTER_PARAMETER_TYPE!>String<!>) {
        field = "$param "
    }

class My {
    var y: Int = 1
        set(param: <!REDUNDANT_SETTER_PARAMETER_TYPE!>Int<!>) {
            field = param - 1
        }

    var z: Double = 3.14
        private set

    var w: Boolean = true
        set(param) {
            field = !param
        }
}