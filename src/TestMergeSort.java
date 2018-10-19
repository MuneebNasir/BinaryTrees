import java.util.*;
/*
    The class implements the merge sort algorithm. Mergesort is a recursive sorting
    algorithm that always provides the same performance, regardless of the initial order.
    Given a sortable list, Mergesort algorithm divide the list into halves,
    sort each half, and then merge the sorted half into one sorted list.

    @aurhor Muneeb Nasir
    @version 2100.4.1
 */
public class TestMergeSort
{
    /*
        The method compares the given Strings lexicographically i.e. words are alphabetically
        ordered based on the alphabetical order of their component letters.
        @param string1, The First String Parameter that is to be considered to be the check
        @param string2, The String that is compared with First string parameter
        @return int, The integer returned would be used to evaluate the order
         -1 (less than 0) as return value if the string1 is alphabetically before string2
         0 as return value if the two strings are the same.
         +1 as return value if the string1 is alphabetically after string2

     */
    public int compareString(String string1, String string2) {
        int result = string1.compareTo(string2);
        return result;
    }


    public String[] sort_Helper(String[] array) throws IllegalArgumentException
    {

        if(array.length==0|| array == null)
        {
            throw new IllegalArgumentException();
        }
        else if(array.length==1)
        {
            return array;
        }
        else
        {
            int midPoint = (array.length)/2;
            String[] left = Arrays.copyOfRange(array,0,midPoint);
            sort_Helper(left); //RECURSIVE CALL TO SORT
            String[] right = Arrays.copyOfRange(array,midPoint,array.length);
            sort_Helper(right);// RECURSIVE CALL TO SORT
            int left_index = 0;
            int right_Index = 0;
            for(int i = 0; i < array.length;i++)
            {
                if(left_index >= left.length)
                {
                    array[i] = right[right_Index];
                    right_Index++;
                }
                else if(right_Index >= right.length)
                {
                    array[i] = left[left_index];
                    left_index++;
                }
                else {
                    int compare = compareString(left[left_index], right[right_Index]);
                    if (compare < 0) {
                        array[i] = left[left_index];
                        left_index++;//Adding the index after the placement
                    } else if (compare > 0) {
                        array[i] = right[right_Index];
                        right_Index++;//Adding the index after the placement
                    } else//If the alphabets are the same
                    {
                        array[i] = left[left_index++];
                        array[++i] = right[right_Index++];
                        //The addition done to increment the current index before evaluation of the index
                    }
                }
            }
        }
        return array;
    }

    /*
        The method takes in the two arrays and merges the two arrays together in a
        single sorted array
        @param array_1, The sorted Right half of the array
        @param array_2, The sorted Left half of the array
        @return Array, with the array_1 and array_2 combined
     */
    public String[] merge(String[] array_1, String[] array_2) {
        int length = array_1.length + array_2.length;
        String[] end = new String[length];
        int a_LEFT = 0;
        int b_RIGHT = 0;
        for (int i = 0; i < end.length; i++) {
            if (a_LEFT < array_1.length && b_RIGHT < array_2.length)
            {
                if (compareString(array_1[a_LEFT], array_2[b_RIGHT]) < 0) {
                    end[i] = array_1[a_LEFT];
                    a_LEFT++;
                } else if (compareString(array_1[a_LEFT], array_2[b_RIGHT]) > 0) {
                    end[i] = array_2[b_RIGHT];
                    b_RIGHT++;
                }
                else if(compareString(array_1[a_LEFT], array_2[b_RIGHT]) == 0)
                {
                    end[i] = array_1[a_LEFT];
                    a_LEFT++;//Increment to check the next element in LEFT ARRAY (FOR NEXT ITERATION)
                    i++;//Increment the value of i as the same words must be next to each other
                    end[i] = array_2[b_RIGHT];
                    b_RIGHT++;//Increment to check the next element in RIGHT ARRAY (FOR NEXT ITERATION)
                }
            }
            else {
                if (a_LEFT >= array_1.length) //CASE 1 When the end of LEFT list have been reached
                {
                    while (b_RIGHT < array_2.length) {
                        end[i] = array_2[b_RIGHT];
                        b_RIGHT++;
                        i++;
                    }
                } else if (b_RIGHT >= array_2.length) //CASE 2 When the end of the RIGHT list have been reached
                {
                    while (a_LEFT < array_1.length) {
                        end[i] = array_1[a_LEFT];
                        a_LEFT++;
                        i++;
                    }
                }
            }

        }
        return end;
    }

    /*
        The method implements the Mergesort Procedure
        @param array, The array that needs to be sorted and arranged.
     */
    public String[] mergeSort(String[] array) throws IllegalArgumentException
    {
        int size = -1;
        int midPoint = 0;
        int left_INDEX = 0;
        int right_INDEX = 0;
        String[] completed_TEMP = null;
        if(array.length<=0 || array == null)
        {
            throw new IllegalArgumentException();
        }
        else if(array.length==1)
        {
            return array;
        }
        else
        {
            size = array.length;
            midPoint = (size + 1)/2;
            //First Half of the given Array
            String[] rightHalf = Arrays.copyOfRange(array,0,midPoint);
            //Second half of the given Array
            String[] leftHalf = Arrays.copyOfRange(array,midPoint,size);


            //Sorting the given Arrays
            String[] temp1 = sort_Helper(rightHalf);
            String[] temp2 = sort_Helper(leftHalf);

            completed_TEMP = merge(temp1,temp2);
        }
        return completed_TEMP;
    }

    public static void main(String[] args)
    {
        TestMergeSort tester = new TestMergeSort();


        //String[] result_MAIN = tester.swapAndSort(input);
        //String[] test_INPUT = {"Lisa", "Adam", "Adam", "Vicky", "George", "Beth", "Lisa", "Liisa","Aaron", "Adam"};
        String[] test_INPUT = {"Lisa", "Adam", "John", "Vicky","George", "Beth", "Kate" , "Aaron", "Jinny"};
        String[] result_MAIN = tester.mergeSort(test_INPUT);

        System.out.println("Initial Array Before The Merge Sort");
        for(int i = 0; i< result_MAIN.length;i++)
        {
            System.out.print(test_INPUT[i]+" ");
        }
        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");
        for(int i = 0; i< result_MAIN.length;i++)
        {
            System.out.print(result_MAIN[i]+" ");
        }
        System.out.println("");
        System.out.println("SORTED ARRAY: Array After Merge Sort");
        /*
        Aaron Adam Beth George Jinny John Kate Lisa Vicky -> 1 MAIN RESULT
        Aaron Adam Beth George Jinny John Kate Lisa Vicky -> MAINNNN
        Aaron Adam Beth George Jinny John Kate Lisa Vicky ->2
        Aaron Adam Adam Adam Beth George Liisa Lisa Lisa Vicky -> 3 CASE with repeated elements
        Aaron Adam Adam Adam Beth George Liisa Lisa Lisa Vicky
        */
    }
}

