import java.util.*;
public class NLNode<T> {
    private NLNode<T> parent;
    private ListNodes<NLNode<T>> children;
    private T data;

    //NLNode has two constructors
    public NLNode() {
        parent = null;
        data = null;
        children = new ListNodes<NLNode<T>>(); //Setting children as an empty ListNode
    }

    public NLNode(T d, NLNode<T> p) {
        data = d;
        parent = p;
        children = new ListNodes<NLNode<T>>(); //Initialzing an empty ListNodes
    }

    //Useful methods
    public void addChild(NLNode<T> newChild) {
        children.add(newChild);
        newChild.setParent(this);
    }

    public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
        Iterator<NLNode<T>> iter = children.sortedList(sorter); //Getting an iterator from the list of ListNodes class using the sorter comparator
        return iter;
    }

    public Iterator<NLNode<T>> getChildren() {
        Iterator<NLNode<T>> iter = children.getList(); //Getting an iterator from the list of ListNodes class without specifying how to sort it
        return iter;
    }

    //Getters
    public NLNode<T> getParent() {
        return parent;
    }
    public T getData() {
        return data;
    }
    //Setters
    public void setParent(NLNode<T> p) {
        parent = p;
    }
    public void setData(T d) {
        data = d;
    }
}
