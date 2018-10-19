/*
    The Class is used for the implementing and initialization of the dictionary that is being
    represented by a Binary Search Tree(BST). The BST is using the REFERENCE-BASED representation
    The GETTER methods used to get the RIGHT/LEFT/ELEMENT of the BST ROOT as this is more conventional
    rather than calling the right/left/element fields(PROTECTED VISIBILITY)
    @author Muneeb Nasir
    @version 2100.4.B
 */

public class BSTDictionary<E,K extends Sortable> implements Dictionary<E, K> {

    private BSTNode<E,K> root;//MAIN NODE OF THE BST
    //The root that is to be used for the development of the Binary Search Tree
    //The ONLY reference that is used to iterate/TRAVERSE over the BST

    /*
        The default constructor of the BST. Constructor always
        has PUBLIC visibility.
     */
    public BSTDictionary()
    {
        root = null;
    }

    /*
        The method returns the node at the specified location i.e. the Key. The starting reference for the method is
        the root node of the Binary Search Tree. This method is required to traverse over the Binary Search Tree
        according to the KEY element specified. MAIN ROOT -> LEFT SUBTREE OR RIGHT SUB-TREE
        BINARY SEARCH ALGORITHM IS IMPLEMENTED
     */
    private BSTNode<E,K> check_Search(BSTNode<E,K> node, K key)
    {
        if(node == null)
        {
            return null;
        }

        else if (key.compareTo(node.getKey()) < 0 && node.getLeft() !=null)
        {
            if(key.compareTo(node.getLeft().getKey()) == 0)
            {
                return node;
            }
            return check_Search(node.getLeft(),key);
        }
        else if (key.compareTo(node.getKey()) > 0 && node.getRight() !=null)
        {
            if(key.compareTo(node.getRight().getKey()) == 0)
            {
                return node;
            }
            return check_Search(node.getRight(),key);
        }
        else
        {
            return node;
        }


    }

    /*
        The method is used to search for a given element in the data structure. The traversal over the Binary Search
        Tree.
        @param Key, The Key is the location of the element specified by the user that is to be found and returned.
        @return Object, The specified item that is found in the BST
        If the object is not found the return value is -1.
     */
    @Override
    public E search(K key) {
        BSTNode<E, K> search_Node = check_Search(root, key);//TO GET THE NODE AT THE SPECIFIED KEY VALUE LOCATION
        if (search_Node == null || search_Node.getRight() == null || search_Node.getLeft() == null) {
            //System.out.println("The Binary Search Tree is Empty. No items inside currently");
            return null;
        } else {
            if (key == search_Node.getKey()) {
                return search_Node.getElement();
            } else if (search_Node.getLeft() != null && search_Node.getRight() != null) {
                if (key.compareTo(search_Node.getLeft().getKey()) == 0) {
                    return search_Node.getLeft().getElement();
                } else if (key.compareTo(search_Node.getRight().getKey()) == 0) {
                    return search_Node.getRight().getElement();
                }
            } else if (search_Node.getRight() == null || search_Node.getLeft() == null)
                //if either the TLeft OR TRight is null, fully check to
                //check all possibilities to search for the specified KEY location
            {
                    if (key.compareTo(search_Node.getLeft().getKey()) == 0) {
                        return search_Node.getElement();
                    }
                    else if (key.compareTo(search_Node.getRight().getKey()) == 0) {
                        return search_Node.getElement();
                    }
            }
        }
        return null;
    }

    /*
        The method is used to insert an element at the given order in the tree. The element to be inserted is
        lexicographically checked and inserted according to the value.
        @param key, The index/location of the Binary Search Tree
        @param element, The object that is to be inserted in the Tree
     */
    @Override
    public void insert(K key, E element)
    {
        if(root == null)//CASE 1: THE CASE WHEN THE TREE IS EMPTY
        {
            root = new BSTNode<>(key,element,null,null);
        }
        else if(key == null || element == null )
        {
            System.out.println("ERROR: No Key Location specified by the User for Insertion");
        }
        else
        {
            BSTNode<E,K> required_node = check_Search(root,key);

            //CASE 2: IF THE VALUE TO BE SPECIFIED KEY IS LESS THAN THE KEY AT THAT NODE
            if(key.compareTo(required_node.getKey()) < 0 && required_node.getLeft() == null)
            {
                required_node.setLeft(new BSTNode<>(key,element,null,null));
            }

            //CASE 3: IF THE VALUE TO BE SPECIFIED KEY IS GREATER THAN THE KEY AT THAT NODE
            else if(key.compareTo(required_node.getKey()) > 0 && required_node.getRight() == null)
            {
                required_node.setRight(new BSTNode<>(key,element,null,null));
            }
        }
    }

    /*
        The method deletes the element accordingly as per the user specified Key value.
        @param key, The location/index of the element that needs to be removed
     */
    @Override
    public void delete(K key) throws IllegalArgumentException
    {
        if(root == null)
        {
            //System.out.println("There are no elements in the Binary Search Tree for removal");
            return;
        }
        else if(key == null)
        {
            //System.out.println("The Key location is not specified by the user");
            throw new IllegalArgumentException();
        }
        else
        {
            BSTNode<E,K> required_Node = check_Search(root,key);
            delete_helper(required_Node,key);
        }
    }

    /*
        This method is the helper method for the deletion of the node at the user specified key
        The method deletes the node according to the location of the node and the children relationship.
        The element in the given BST is deleted by the helper method using recursion process
        @param Node, that is BST Node at the specified KEY Point of the BST
        @param key, the location specified by the user for the removal
     */
    private BSTNode<E,K> delete_helper(BSTNode<E,K> node, K key)
    {
        if(node == null)//BASE CASE for the recursive process
        {
            return null;
        }
        int compare_check = key.compareTo(node.getKey());
        //After checking all the conditions the item is removed and the TREE is updated to the newest settings
        //This is to correct the link after the deletion process
        if(compare_check < 0)//To retain the order of BST
        {
            node.setLeft(delete_helper(node.getLeft(),key));
        }
        else if(compare_check > 0)//To retain the order of BST
        {
            node.setRight(delete_helper(node.getRight(),key));
        }
        else //The deletion process according to the different cases of the BST
        {

            //CASE 1: If the node to be removed has no children i.e. SUB-TREE nodes
            if (node.getRight() == null && node.getLeft() == null) {
                //System.out.println("The item is successfully removed");
                return node;
            }

            //CASE 2: If there exists only one children node of the tree either Tree_Left OR Tree_Right is null
            //The value of the key to be deleted and child key is compared to modify and update existing linkage
            //Left child is null
            else if (node.getLeft() == null && node.getRight() != null) {

                //System.out.println("The item is successfully removed")
                return node.getRight();//Changing the current reference of the node
            }
            //Right Child is null
            else if (node.getLeft() != null && node.getRight() == null) {

                //System.out.println("The item is successfully removed at location");
                return node.getLeft();//Changing the current reference of the node
            }
            //CASE 3: If there required node at the specified key has two children
            //This requires the check for the INORDER PREDECESSOR
            else
            {
                BSTNode<E,K> inorder_P = find_PREDECESSOR(node.getRight());
                node.setRight(delete_helper(inorder_P.getRight(),inorder_P.getKey()));
            }
        }
        return node;//The last return call ends the entire function and returns the final result
    }

    /*
        This method is used to find the INORDER PREDECESSOR of the Binary Search Tree and this is used in
        the deletion method. The INORDER PREDECESSOR is the least element in the RIGHT SUB-TREE from the
        node that has 2 children.
        @param Node, The Node of the given BST
        @return the node with the maximum value
     */
    private BSTNode<E,K> find_PREDECESSOR(BSTNode<E,K> current)
    {
        if(current == null)
        {
            return null;
        }

        while(current.getLeft() != null)
        //Iterating over the remaining BST section to get the maximum value
        // that is at the left side of BST for accurate INORDER PREDECESSOR
        {
            current = current.getLeft();
        }
        return current;
    }

    /*
        The method uses the INORDER Depth-First traversal algorithm to traverse over the Binary Search Tree
        and print the element accordingly. The INORDER traversal would preserve the non-decreasing order of the Tree.
        INORDER => Left Sub-tree -> MAIN ROOT -> Right-Sub-tree
     */
    @Override
    public void printTree()
    {
        System.out.println("INORDER: ");
        print_Helper(root);
        System.out.println();
        //System.out.println("End of the Binary Search Tree");
    }

    /*
        This method is the Helper method that is used to implement the printTree() method. The output of teh BST tree
        data is to be INORDER. The process used to print the TRESS is implemented
        using RECURSIVE PROCESS.
        @param Root, The MAIN node of the ROOT
     */
    private void print_Helper(BSTNode<E,K> main)
    {
        if(main == null)
        {
            return;
        }
        print_Helper(main.getLeft());
        System.out.print(main.getElement() + " ");
        print_Helper(main.getRight());
    }



    /*
        The depth method is used to return the HEIGHT of the tree. The Height of the tree is the numbers of levels from
        the ROOT to the LEAF. If the tree is empty the height is zero.
        Height is also refereed as the MAXIMUM DEPTH of the BST. The process used will be a recursive process that is
        used to check the longest path of the BST
        @return Height, The height of the tree.

     */
    @Override
    public int depth()
    {
        return depth_Helper(root);
    }

    /*
        The method is used as a helper method for determining the height of the BST
        @param Root, the reference to the ROOT of the BST
        @return Height, returns the height of the BST
        1 is added to the maximum depth is to also consider the ROOT of the BST
        0 will be returned if the BST is empty
     */
    private int depth_Helper(BSTNode<E,K> main)
    {
        if(main == null)
        {
            return 0;
        }
        int left_Height = depth_Helper(main.getLeft());
        int right_Height = depth_Helper(main.getRight());
        int max = Math.max(left_Height,right_Height);
        return  max + 1;


    }
}
