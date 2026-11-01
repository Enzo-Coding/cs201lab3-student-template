import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BinaryTree {
     private int[] tree = new int[500000];

    public BinaryTree(int data) {
        tree[0] = data;
    }

    public int getRoot() {
        return tree[0];
    }

    public int parent(int idx) {
          return (idx - 1) / 2;
    }

    public int left_child(int idx) {
     return idx * 2 + 1;
    }

    public int right_child(int idx) {
     return idx * 2 + 2;
    }

    public void add(int data, int idx) {
        int target = tree[idx];
        if (data < target) {
          if (tree[left_child(idx)] == 0) {
               tree[left_child(idx)] = data;
          } else {
               add(data, left_child(idx));
          }
        } else {
          if (tree[right_child(idx)] == 0) {
               tree[right_child(idx)] = data;
          } else {
               add(data, right_child(idx));
          }
        }
    }

    public Integer[] postorder() {
     int start = 0;
     ArrayList<Integer> arr = new ArrayList<>();

     addToPostorder(arr, start);
     return arr.toArray(new Integer[arr.size()]);
    }

    public void addToPostorder(List<Integer> arr, int idx) {
          if (tree[idx] == 0) {
               return;
          }

          addToPostorder(arr, left_child(idx));
          addToPostorder(arr, right_child(idx));
          arr.add(tree[idx]);
    }
}

public class Q1Test {

     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {

          String[] split1 = traversal1.split("-");
          String[] split2 = traversal2.split("-");
          String[] split3 = traversal3.split("-");

          if (split1.length != split2.length || split2.length != split3.length) {
               return "Invalid traversals";
          }

          Integer[][] numTraversals = new Integer[3][];

          numTraversals[0] = fromStringToInt(split1);
          numTraversals[1] = fromStringToInt(split2);
          numTraversals[2] = fromStringToInt(split3);

          if (!haveSameNumbers(numTraversals)) {
               return "Invalid traversals";
          }

          int inorderTraversalIdx = -1;
          for (int i = 0; i < numTraversals.length; i++) {
               if (isIncreasingSequence(numTraversals[i])) {
                    inorderTraversalIdx = i;
                    break;
               }
          }

          if (inorderTraversalIdx == -1) {
               return "Invalid traversals";
          }

          Integer[][] remainingTraversals = new Integer[2][];
          int idx = 0;
          for (int i = 0; i < numTraversals.length; i++) {
               if (i == inorderTraversalIdx) {
                    continue;
               }

               remainingTraversals[idx] = numTraversals[i];
               idx++;
          }

          boolean traversal1RootEqualTraversal2Tail 
          = remainingTraversals[0][0].equals(remainingTraversals[1][remainingTraversals[1].length - 1]);

          boolean traversal2RootEqualTraversal1Tail 
          = remainingTraversals[1][0].equals(remainingTraversals[0][remainingTraversals[0].length - 1]);
          
          // Check for linear binary tree
          if (traversal1RootEqualTraversal2Tail && traversal2RootEqualTraversal1Tail) {
               if ((!isDecreasingSequence(remainingTraversals[1]) || !isIncreasingSequence(remainingTraversals[0]))
                    || ((!isDecreasingSequence(remainingTraversals[0]) || !isIncreasingSequence(remainingTraversals[1])))) {
                    return "Invalid traversals";
               }

               if (isDecreasingSequence(remainingTraversals[0])) {
               switch (inorderTraversalIdx) {
                    case 0:
                         return formatTraversalString(1, 0, 2);
                    case 1:
                         return formatTraversalString(0, 1, 2);
                    case 2:
                         return formatTraversalString(0, 2, 1);
                    }
               } else {
                    switch (inorderTraversalIdx) {
                    case 0:
                         return formatTraversalString(2, 0, 1);
                    case 1:
                         return formatTraversalString(2, 1, 0);
                    case 2:
                         return formatTraversalString(1, 2, 0);
                    }  
               }
          }

          if (!traversal1RootEqualTraversal2Tail && !traversal2RootEqualTraversal1Tail) {
               return "Invalid Traversals";
          }

          int preorderTraversalIdx = -1;
          int postorderTraversalIdx = -1;

          if (traversal1RootEqualTraversal2Tail) {
               switch (inorderTraversalIdx) {
                    case 0:
                         preorderTraversalIdx = 1;
                         postorderTraversalIdx = 2;
                         break;
                    case 1:
                         preorderTraversalIdx = 0;
                         postorderTraversalIdx = 2;
                         break;
                    case 2:
                         preorderTraversalIdx = 0;
                         postorderTraversalIdx = 1;
                         break;
               }
          } else {
               switch (inorderTraversalIdx) {
                    case 0:
                         preorderTraversalIdx = 2;
                         postorderTraversalIdx = 1;
                         break;
                    case 1:
                         preorderTraversalIdx = 2;
                         postorderTraversalIdx = 0;
                         break;
                    case 2:
                         preorderTraversalIdx = 1;
                         postorderTraversalIdx = 0;
                         break;              
               } 
          }

          // Integer[] preorder = numTraversals[preorderTraversalIdx];
          // Integer[] postorder = numTraversals[postorderTraversalIdx];
          // Integer[] inorder = numTraversals[inorderTraversalIdx];

          // boolean[] result = new boolean[1];
          // result[0] = true;

          // Map<Integer, Integer> map = new HashMap<>();
          // for (int i = 0; i < inorder.length; i++)
          //      map.put(inorder[i], i);

          // util(preorder, map, postorder, 0, inorder.length - 1, 0, postorder.length - 1, result);

          // if (result[0]) {
          //      return formatTraversalString(preorderTraversalIdx, inorderTraversalIdx, postorderTraversalIdx);
          // } else {
          //      return "Invalid Traversals";
          // }

          BinaryTree tree = new BinaryTree(numTraversals[preorderTraversalIdx][0]);
          for (int i = 1; i < numTraversals[preorderTraversalIdx].length; i++) {
               tree.add(numTraversals[preorderTraversalIdx][i], 0);
          }

          Integer[] postorder = tree.postorder();
          if (postorder.length != numTraversals[preorderTraversalIdx].length) {
               return "Invalid traversals";
          }

          for (int i = 0; i < numTraversals[postorderTraversalIdx].length; i++) {
               if (!postorder[i].equals(numTraversals[postorderTraversalIdx][i])) {
                    return "Invalid traversals";
               }
          }

          return formatTraversalString(preorderTraversalIdx, inorderTraversalIdx, postorderTraversalIdx);
     }

//      private static void util(Integer[] pre, Map<Integer, Integer> map, Integer[] post, int inStart, int inEnd, int preIndex, int postIndex, boolean[] result) {
//         if (inStart > inEnd)
//             return;

//         Integer mid = map.get(pre[preIndex]);
//         if (mid == null || pre[preIndex] != post[postIndex]) {
//             result[0] = false;
//             return;
//         }

//         // check for left part of tree
//         util(pre, map, post, inStart, mid - 1, preIndex + 1, postIndex - (inEnd - mid) - 1, result);

//         // check for right part of tree
//         util(pre, map, post, mid + 1, inEnd, preIndex + (mid - inStart) + 1, postIndex - 1, result);
//     }


     private static Integer[] fromStringToInt(String[] str) {
          return Arrays.stream(str).map(Integer::parseInt).toArray(Integer[]::new);
     }

     private static boolean haveSameNumbers(Integer[][] traversals) {
          for (int i = 0; i < traversals[0].length; i++) {
               int target = traversals[0][i];

               for (int j = 1; j < traversals.length; j++) {
                    boolean  foundTarget = false;

                    for (int k = 0; k < traversals[j].length; k++) {
                         if (traversals[j][k] == target) {
                              foundTarget = true;
                              break;
                         }
                    }

                    if (!foundTarget) {
                         return false;
                    }
               }
          }

          return true;
     }

     private static boolean isIncreasingSequence(Integer[] traversal) {
          for (int i = 1; i < traversal.length; i++) {
               if (traversal[i] <= traversal[i - 1]) {
                    return false;
               }
          }

          return true;
     }

     private static boolean isDecreasingSequence(Integer[] traversal) {
          for (int i = 1; i < traversal.length; i++) {
               if (traversal[i] >= traversal[i - 1]) {
                    return false;
               }
          }

          return true;
     }
     
     private static String formatTraversalString(int preorderIdx, int inorderIdx, int postorderIdx) {
          String result = "";

          for (int i = 0; i < 2; i++) {
               result += "Traversal " + (i + 1) + " - ";

               if (i == preorderIdx) {
                    result += "Preorder";
               } else if (i == inorderIdx) {
                    result += "Inorder";
               } else if (i == postorderIdx) {
                    result += "Postorder";
               }

               result += ", ";
          }

               result += "Traversal 3 - ";

               if (2 == preorderIdx) {
                    result += "Preorder";
               } else if (2 == inorderIdx) {
                    result += "Inorder";
               } else if (2 == postorderIdx) {
                    result += "Postorder";
               }

          return result;
     }

}