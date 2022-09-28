package tds

class Node<T>(val elem: T, val next: Node<T>?)

interface IStack<T> {
    fun isEmpty(): Boolean
    fun top(): T
}

private fun throwEmpty(): Nothing = throw NoSuchElementException("empty stack")

/* TODO: Eliminate this abstract class because it's just the base of MutableStack */
abstract class BaseStack<T>: IStack<T> {
    protected abstract val head: Node<T>?
    protected val headNotNull get() = head ?: throwEmpty()
    override fun isEmpty() = head==null
    final override fun top() = headNotNull.elem
}

class MutableStack<T>: BaseStack<T>(), IStack<T> {
    override var head: Node<T>? = null
    fun push(elem: T) { head = Node(elem,head) }
    fun pop(): T = headNotNull.also { head = it.next }.elem
}

interface Stack<T>: IStack<T> {
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
}

private class EmptyStack<T>: Stack<T> {
    override fun isEmpty() = true
    override fun top() = throwEmpty()
    override fun pop() = throwEmpty()
    override fun push(elem: T) = NoEmptyStack(Node(elem,null))
}

private class NoEmptyStack<T>(private val head: Node<T>): Stack<T> {
    override fun isEmpty() = false
    override fun top() = head.elem
    override fun pop() = if (head.next==null) EmptyStack() else NoEmptyStack(head.next)
    override fun push(elem: T) = NoEmptyStack(Node(elem,head))
}

/* TODO: No longer used. Replaced by EmptyStack and NoEmptyStack
private class ImmutableStack<T> private constructor(override val head: Node<T>?): BaseStack<T>(), Stack<T> {
    constructor() : this(null)
    override fun push(elem: T) = ImmutableStack(Node(elem,head))
    override fun pop() = ImmutableStack(headNotNull.next)
    override fun isEmpty(): Boolean {
        print("Stack<T>:isEmpty ")
        return super.isEmpty()
    }
} */

fun <T> emptyStack(): Stack<T> = EmptyStack()

// TODO: Optimize this implementation by building just one instance of NoEmptyStack
fun <T> stackOf(vararg elems: T): Stack<T> {
    var s = emptyStack<T>()
    elems.forEach { s = s.push(it) }
    return s
}



