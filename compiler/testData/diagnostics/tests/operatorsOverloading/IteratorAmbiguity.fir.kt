//KT-1821 Write test for ITERATOR_AMBIGUITY diagnostic

interface MyCollectionInterface {
}

interface MyAnotherCollectionInterface {
}

class MyCollection : MyCollectionInterface, MyAnotherCollectionInterface {
}

fun MyCollectionInterface.iterator() = MyIterator()

fun MyAnotherCollectionInterface.iterator() = MyIterator()

class MyIterator {
    fun next() : MyElement = MyElement()
    fun hasNext() = true
}

class MyElement

fun test1(collection: MyCollection) {
    collection.<!OVERLOAD_RESOLUTION_AMBIGUITY!>iterator<!>()
    <!OVERLOAD_RESOLUTION_AMBIGUITY, UNRESOLVED_REFERENCE, UNRESOLVED_REFERENCE!>for (element in collection) {
    }<!>
}