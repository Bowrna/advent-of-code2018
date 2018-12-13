import java.util.Arrays;

class RepeatElement 
{ 
    void printRepeating(int arr[], int size)  
    { 
        int count[] = new int[size]; 
        int i; 
        System.out.println("count array:"+Arrays.toString(count));
        System.out.println("Repeated elements are : "); 
        for (i = 0; i < size; i++)  
        { 
            if (count[arr[i]] == 1) 
                System.out.print(arr[i] + " "); 
            else
                count[arr[i]]++; 
            System.out.println("Array specific element:"+arr[i]);
            System.out.println("count array in loop:"+Arrays.toString(count)); 
        } 
    } 
  
    public static void main(String[] args) 
    { 
        RepeatElement repeat = new RepeatElement(); 
        //This works if we deleted the last 2 100's. It accepts only data smaller than size of array.
        int arr[] = {4, 2, 4, 5, 2, 3, 1,100,100}; 
        int arr_size = arr.length; 
        repeat.printRepeating(arr, arr_size); 
    } 
} 
