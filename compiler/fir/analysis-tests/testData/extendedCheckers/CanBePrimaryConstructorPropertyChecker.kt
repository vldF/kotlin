//WITH_RUNTIME

// Inspection should not work here anywhere
class Incorrect(val property: String, withGetter: Double, withSetter: Int, differentType: String) {

    var withSetter = withSetter
        private set

    val another = property

    val fromProperty = another

    val withGetter = withGetter
        get() = 2 * field

    val differentType: String? = differentType

    constructor(param: Int): this("", 0.0, param, "") {
        val local = param
    }
}

abstract class X2(property: String) {
    init {
        print(property)
    }

    open val property: String = property
}

// Case from KT-12876: also should not work, property lives in different class here
interface Foo {
    val x : String
}

// KT-32561
open class X(property: String) {
    init {
        print(property)
    }

    open val property: String = property
}

class Bar(x : String) {
    init {
        object : Foo {
            override val x = x // Property is assigned to parameter, can be declared in ctor
        }
    }
}

sealed class X3(property: String) {
    init {
        print(property)
    }

    open val property: String = property
}

open class X4(property: String) {
    init {
        print(property)
    }

    <!CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY!>val property: String = property<!>
}

class X5(property: String) {
    init {
        print(property)
    }

    <!CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY!>open val property: String = property<!>
}

open class X6(property: String) {
    <!CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY!>open val property: String = property<!>
}


class Correct(simple: String, withType: Int, otherName: Double) {

    <!CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY!>val simple = simple<!>

    <!CAN_BE_PRIMARY_CONSTRUCTOR_PROPERTY!>val withType: Int = withType<!>

    // Questionable case (due to possible named parameters), not allowed now
    val anotherName = otherName
}
