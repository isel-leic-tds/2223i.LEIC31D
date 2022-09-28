package tds

class Node<T>(val elem: T, val next: Node<T>?)

class MutableStack<T> {
    private var head: Node<T>? = null
    private val headNotNull
        get() = head ?: throw NoSuchElementException("empty stack")
    fun push(elem: T) { head = Node(elem,head) }
    fun pop(): T = headNotNull.also { head = it.next }.elem
    fun isEmpty() = head==null
    fun top() = headNotNull.elem
}

class Stack<T> private constructor(private val head: Node<T>?) {
    constructor() : this(null)
    fun push(elem: T): Stack<T> = Stack(Node(elem,head))
    private val headNotNull by lazy {
        head ?: throw NoSuchElementException("empty stack")
    }
    fun pop() = Stack(headNotNull.next)
    fun isEmpty(): Boolean = head==null
    fun top() = headNotNull.elem
}


