package com.familytree;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class FamilyTree extends BinaryTree<String> {

    /**
     * Pattern to check the prefix of a nickname
     */
    private static final String NICKNAME_PATTERN = "(ebu-)(.+)|(ibn-)(.+)";

    private final int PREFIX_LEGTH = 4;


    public FamilyTree(String firstInTheFamily) {
        super(firstInTheFamily, null, null);
    }

    //PUBLIC METHOD
    /**
     * Inserts a new person in appropriate position into family tree.
     *
     * @param person The name of a person to be added
     * @param personsParent The name of the person's parent
     * @param personsParentNickname The nickname of the person's parent(The nickname is given as in Arabic culture)
     * @return true
     * @throws FamilyTreeException if person could not be inserted.
     */
    public boolean add(String person, String personsParent, String personsParentNickname) throws FamilyTreeException {
        if (!Pattern.matches(NICKNAME_PATTERN, personsParentNickname))
            throw new IllegalArgumentException(personsParentNickname);

        if (size() == 1) {
            if (this.getData().equals(personsParent) && isParent(personsParentNickname) &&
                    deletePrefix(personsParentNickname).equals(person)) {
                root.left = new Node<>(person);
                ++size;
                return true;
            }

            throw new FamilyTreeException(person + "," + personsParent + "," + personsParentNickname);
        } else {
            if (isParent(personsParentNickname)) {
                if (person.equals(deletePrefix(personsParentNickname))) {
                    if (countMatches(root, personsParent, person, (str1, str2) -> str1 + str2) > 0 ||
                            countMatches(root, personsParent) != 1)
                        throw new FamilyTreeException(person + "," + personsParent + "," + personsParentNickname);

                    addChild(findPerson(root, personsParent), person);
                } else {
                    if (countMatches(root, personsParent, deletePrefix(personsParentNickname),
                            (str1, str2) -> str1 + str2) != 1)
                        throw new FamilyTreeException(person + "," + personsParent + "," + personsParentNickname);

                    addChild(findPerson(root, personsParent, deletePrefix(personsParentNickname),
                            (str1, str2) -> str1 + str2), person);
                }
            } else //persons parent is somebodies child
            {
                if (countMatches(root, personsParent, deletePrefix(personsParentNickname),
                        (str1, str2) -> str2 + str1) != 1)
                    throw new FamilyTreeException(person + "," + personsParent + "," + personsParentNickname);

                Node<String> parentOfPersonsParent = findPerson(root, personsParent, deletePrefix(personsParentNickname),
                        (str1, str2) -> str2 + str1);
                Node<String> _personsParent = getChild(parentOfPersonsParent.left, personsParent);
                addChild(_personsParent, person);
            }

            return true;
        }
    }

    /**
     * Returns an iterator over the elements contained in this tree.
     * Iterator traverses tree in level-order.
     * @return an iterator over the elements contained in this tree
     */
    public Iterator<String> levelOrderIterator()
    {
        return new FamilyTreeLevelOrderIterator();
    }

    //----------------------- Level Order Iterator class ------------------------//
    private class FamilyTreeLevelOrderIterator extends BinaryTreeIterator implements Iterator<String>
    {
        protected void next(Deque<Node<String>> nextNodeQueue) {
            if (!hasNext())
                throw new NoSuchElementException();

            nextNode = nextNodeQueue.poll();
            if (nextNode.left != null)
                nextNodeQueue.offer(nextNode.left);
            if (nextNode.right != null)
                nextNodeQueue.offer(nextNode.right);


            previousNode = nextNode;
        }
    }

    //PRIVATE METHODS
    //----------------------------------------------------------------------------------------------------------------//
    private void addChild(Node<String> root, String child) {
        if (root.left == null)
            root.left = new Node<>(child);
        else
            addSibling(root.left, child);
    }

    private void addSibling(Node<String> root, String sibling) {
        if (root.right == null)
            root.right = new Node<>(sibling);
        else
            addSibling(root.right, sibling);
    }

    private Node<String> getChild(Node<String> root, String child) {
        if (root == null)
            return null;
        else if (root.data.equals(child))
            return root;
        else return getChild(root.right, child);
    }

    private Node<String> findPerson(Node<String> root, String person) {
        Node<String> leftTreeSearchResult;
        Node<String> rightTreeSearchResult;
        if (root == null)
            return null;
        else if (root.data.equals(person))
            return root;
        else {
            leftTreeSearchResult = findPerson(root.left, person);
            rightTreeSearchResult = findPerson(root.right, person);
            return (leftTreeSearchResult != null ? leftTreeSearchResult : rightTreeSearchResult);
        }
    }

    private Node<String> findPerson(Node<String> root, String person, String personsRelative,
                                    BiFunction<String, String, String> concatenate) {
        Node<String> leftTreeSearchResult;
        Node<String> rightTreeSearchResult;
        if (root == null)
            return null;
        else if (root.left != null
                && countMatchesInPersonsChildren(root.left, person, personsRelative, root.data, concatenate) == 1)
            return root;
        else {
            rightTreeSearchResult = findPerson(root.right, person, personsRelative, concatenate);
            leftTreeSearchResult = findPerson(root.left, person, personsRelative, concatenate);

            return (leftTreeSearchResult != null ? leftTreeSearchResult : rightTreeSearchResult);
        }
    }

    private int countMatches(Node<String> root, String person) {
        if (root == null)
            return 0;

        return ((root.data.equals(person) ? 1 : 0) +
                countMatches(root.right, person) + countMatches(root.left, person));
    }

    private int countMatches(Node<String> root, String person, String personsRelative,
                             BiFunction<String, String, String> concatenate) {
        int count = 0;

        if (root == null)
            return count;

        if (root.left != null) {
            if (concatenate.apply(root.data, root.left.data).equals(person + personsRelative))
                ++count;
            else if (root.left.right != null)
                count += countMatchesInPersonsChildren(root.left.right, person, personsRelative, root.data, concatenate);
        }

        count += countMatches(root.right, person, personsRelative, concatenate)
                + countMatches(root.left, person, personsRelative, concatenate);

        return count;
    }

    private int countMatchesInPersonsChildren(Node<String> root, String person, String personsRelative, String possiblePerson,
                                              BiFunction<String, String, String> concatenate) {
        if (root == null)
            return 0;
        else if (concatenate.apply(possiblePerson, root.data).equals(person + personsRelative))
            return 1;
        else
            return countMatchesInPersonsChildren(root.right, person, personsRelative, possiblePerson, concatenate);
    }


    private boolean isParent(String personNickname) {
        return personNickname.contains("ebu-");
    }

    private String deletePrefix(String personNickname) {
        return personNickname.substring(PREFIX_LEGTH);
    }
}
