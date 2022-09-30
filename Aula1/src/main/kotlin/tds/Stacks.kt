package tds

private class Node<T>(val elem: T, val next: Node<T>?)

interface IStack<T> : Iterable<T> {
    fun isEmpty(): Boolean
    fun top(): T
}

private fun throwEmpty(): Nothing = throw NoSuchElementException("empty stack")

private class NodeIter<T>(private var after:Node<T>?) : Iterator<T> {
    override fun hasNext() = after!=null
    override fun next() =
        (after?:throw NoSuchElementException()).also { after=it.next }.elem
}

class MutableStack<T>: IStack<T> {
    private var head: Node<T>? = null
    private val headNotNull get() = head ?: throwEmpty()
    override fun isEmpty() = head==null
    override fun top() = headNotNull.elem
    override fun iterator(): Iterator<T> = NodeIter(head)
    fun push(elem: T) { head = Node(elem,head) }
    fun pop(): T = headNotNull.also { head = it.next }.elem
}

interface Stack<T>: IStack<T> {
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
}

private object EmptyIter : Iterator<Nothing> {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}

private class EmptyStack<T>: Stack<T> {
    override fun isEmpty() = true
    override fun top() = throwEmpty()
    override fun pop() = throwEmpty()
    override fun push(elem: T) = NoEmptyStack(Node(elem,null))
    override fun iterator() = EmptyIter
}

private class NoEmptyStack<T>(private val head: Node<T>): Stack<T> {
    override fun isEmpty() = false
    override fun top() = head.elem
    override fun pop() = if (head.next==null) EmptyStack() else NoEmptyStack(head.next)
    override fun push(elem: T) = NoEmptyStack(Node(elem,head))
    override fun iterator(): Iterator<T> = NodeIter(head)
}

fun <T> emptyStack(): Stack<T> = EmptyStack()

fun <T> stackOf(vararg elems: T): Stack<T> =
    if (elems.isEmpty()) EmptyStack()
    else NoEmptyStack( elems.fold(null as Node<T>?){ node, e -> Node(e,node) } as Node<T> )



