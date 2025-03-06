public class Node extends ListItem{
    public Node(Object value) {
        super(value);
    }

    @Override
    ListItem next() {
        return this.rightLink;
    }

    @Override
    ListItem setNext(ListItem rightLink) {
        this.rightLink = rightLink;
        return this.rightLink;
    }

    @Override
    ListItem previous() {
        return this.leftLink;
    }

    @Override
    ListItem setPrevious(ListItem leftLink) {
        this.leftLink = leftLink;
        return this.leftLink;
    }

    @Override
    int compareTo(ListItem listItem) {
        if (listItem != null) {
            return ((String) super.getValue()).compareTo((String) listItem.getValue());
        } else {
            return -1;
        }
    }
}
