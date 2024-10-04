import java.util.*;

public class Main {

    // TODO : Swap without extra space.
    public static void swap(int[] arr, int i, int j){
        arr[i] = arr[i] ^ arr[j];   // Or can be achieved by subtraction.
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void printArray(int[] arr){
        Arrays.stream(arr).forEach(n -> System.out.printf("%d\t", n));
        System.out.println();
    }

    // TODO : Bubble sort
    public static void bubbleSort(int[] arr){
        for(int i=0; i<arr.length-1; i++)
            for(int j=0; j<arr.length-1-i; j++)
                if(arr[j] > arr[j+1])
                    swap(arr, j, j+1);
    }

    // TODO : Selection sort
    public static void selectionSort(int[] arr){
        int min;
        for(int i=0; i<arr.length; i++){
            min = i;
            for(int j=i; j<arr.length; j++){
                if(arr[j] < arr[min])
                    min = j;
            }
            swap(arr, i, min);
        }
    }

    // TODO : Insertion sort
    public static void insertionSort(int[] arr){
        for(int i=0; i<arr.length; i++){
            int current = arr[i];
            int j = i - 1;
            while(j >= 0 && arr[j] > current){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = current;
        }
    }

    // TODO : Merge sort
    public static void divide(int[] arr, int si, int ei){
        if(si >= ei) return;
        int mid = si + (ei - si) / 2;
        divide(arr, si, mid);
        divide(arr, mid+1, ei);
        conquer(arr, si, mid, ei);
    }
    public static void conquer(int[] arr, int si, int mid, int ei){
        int i=si, j=mid+1, k=0;
        int[] merged = new int[ei - si + 1];
        while(i<=mid && j<=ei)
            merged[k++] = (arr[i] < arr[j])?arr[i++]:arr[j++];
        while(i<=mid)
            merged[k++] = arr[i++];
        while(j<=ei)
            merged[k++] = arr[j++];
        for(i=0; i<merged.length; i++)
            arr[si+i] = merged[i];
    }

    // TODO : Quick sort
    public static void quickSort(int[] arr, int low, int high){
        if(low >= high) return;
        int partitionIndex = partition(arr, low, high);
        quickSort(arr, low, partitionIndex - 1);
        quickSort(arr, partitionIndex + 1, high);
    }
    public static int partition(int[] arr, int low, int high){
        int i = low-1, pivot = arr[high], j;
        for(j=low; j<high; j++){
            if(arr[j] < pivot){
                i++;
                swap(arr, i, j);
            }
        }
        i++;
        swap(arr, i, high);
        return i;
    }

    // TODO : Count sort (Using array)
    public static void countSort1(int[] arr){
        int max = Arrays.stream(arr).max().getAsInt(), index = 0;
        max++;
        int[] count = new int[max];
//        Arrays.fill(count, 0); // It's not necessary.
        Arrays.stream(arr).forEach(n ->count[n]++);
        for(int i=0; i<max; i++){
            while(count[i] != 0){
                arr[index++] = i;
                count[i]--;
            }
        }
    }

    // TODO : Count sort (Using HashMap)
    public static void countSort2(int[] arr){
        int max = Arrays.stream(arr).max().getAsInt(), min = Arrays.stream(arr).min().getAsInt(), index=0;
        HashMap<Integer, Integer> map = new HashMap<>();
        Arrays.stream(arr).forEach(n -> map.put(n, map.getOrDefault(n, 0)+1));
        for(int i=min; i<max+1; i++){
            if(map.containsKey(i)){
                for(int j=0; j<map.get(i); j++)
                    arr[index++] = i;
            }
        }
    }

    // TODO : Count sort (Negative numbers, if you subtract min(smallest -ve) from itself you will get zero)
    public static void countSort3(int[] arr){
        int max = Arrays.stream(arr).max().getAsInt(), min = Arrays.stream(arr).min().getAsInt(), index = 0;
        int[] count = new int[max - min + 1];
        Arrays.stream(arr).forEach(n -> count[n-min]++);
        for(int i=min; i<max+1; i++){
            while(count[i-min] != 0){
                arr[index++] = i;
                count[i-min]--;
            }
        }
    }

    // TODO : Radix sort (Use bucket of 10, preserve the array until last exp, store the digits in bucket)
    public static void countSort4(int[] arr, int exp){
        int len = arr.length;
        int[] count = new int[10];
        int[] output = new int[len];
        Arrays.stream(arr).forEach(n -> count[n/exp%10]++);
        for(int i=1; i<10; i++)
            count[i] = count[i-1] + count[i];
        for(int i=len-1; i>=0; i--){
            output[count[arr[i]/exp%10]-1] = arr[i];
            count[arr[i]/exp%10]--;
        }
        System.arraycopy(output, 0, arr, 0, len);
    }
    public static void radixSort(int[] arr){
        int max = Arrays.stream(arr).max().getAsInt();
        for(int exp=1; max/exp > 0; exp*=10)
            countSort4(arr, exp);
    }

    public static void main(String[] args){
        int[] arr = {-3, -21, 1, 8, 23, -6, 4};
        printArray(arr);
        countSort3(arr);
        printArray(arr);
    }
}
