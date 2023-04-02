import java.util.*;
public class FileStructure {
    private NLNode<FileObject> root;
    List<String> fileHolder; //Making a variable inside the class so I can access it from multiple classes
    public FileStructure(String fileObjectName) throws FileObjectException {
        FileObject fo = new FileObject(fileObjectName);
        root = new NLNode<FileObject>();
        root.setData(fo);

        //If its a folder we can use an auxiliary recursive algorithm to explore the folder and create the data structure
        //Since we are supposed to only use this on a folder, it doesnt matter here as if its a file, the recursive method will ignore it
        fileTraversal(root);

    }

    private void fileTraversal(NLNode<FileObject> r) {
        FileObject f = r.getData(); //Getting the file object stored in the NLNode
        //Base case is if its a file and the method doesnt need to do anything
        if (f.isFile()) {
            return; //Doing nothing because its a file and has no children
        }
        //Recursive case
        else { //If its a directory
            if (f.directoryFiles() != null) {
                Iterator<FileObject> iterFiles = f.directoryFiles(); //Creating an iterator to go through the files in the directory of the file
                FileObject fileInfo; //Creating a temp object to store the name of the file

                while (iterFiles.hasNext()) { //Looping through the files of the directory
                    fileInfo = iterFiles.next(); //Iterating through the directory files
                    //System.out.println(fileInfo.getName());
                    NLNode<FileObject> newNode = new NLNode<FileObject>(fileInfo, r); //Creating a new node newNode
                    r.addChild(newNode); //Adding r as a child node to r
                    fileTraversal(newNode); //Recursively going through the method
                }
            }

        }

    }

    public Iterator<String> filesOfType(String type) {
        //Creating a container, arraylist, to store the values
        fileHolder = new ArrayList<String>();
        filesOfTypeTraversal(root, type); //Recursively going through the nodes

        return fileHolder.iterator(); //Returning an iterator of the arrayList
    }
    private void filesOfTypeTraversal(NLNode<FileObject> r, String type) {
        FileObject f = r.getData(); //Getting the file object from r

        if (f.isFile()) { //If it's a file
            String name = f.getLongName(); //Get the full address of the file
            if (name.endsWith(type)) { //Check if it ends with the file type we want
                fileHolder.add(name); //If it does add it to the arraylist
            }
        } else { //otherwise if it is a folder
            Iterator<NLNode<FileObject>> iter = r.getChildren(); //Creating an iterator
            while (iter.hasNext()) { //Looping through the children of the node
                NLNode<FileObject> newNode = iter.next(); //For each child node in the folder
                filesOfTypeTraversal(newNode, type); //Going recursively through every node
            }
        }
    }

    public String findFile(String name) {
        //Trying to find the specific file in FileStructure
        //return findFileTraversal(root, name); //Will return the node if it can find it, an empty string if it cant
        return findFileWithArrayList(name);
    }

    private String findFileWithArrayList(String name) {
        int index = name.lastIndexOf("."); //Getting the type of the file
        if (index == -1) { //If there isnt a file like that return ""
            return "";
        }
        //Recursively go through the tree and putting all files of the specified type in an iterator
        Iterator<String> iter = filesOfType(name.substring(index));

        while (iter.hasNext()) { //Looping through the iterator
            String s = iter.next(); //Setting the value in the iterator to s
            if (s.contains(name)) { //If the full file name contains the name we are looking for
                return s; //Then return the full name
            }
        }
        return ""; //If its not there then return an empty string
    }
    private String findFileTraversal(NLNode<FileObject> r, String name) {
        //Base case
        FileObject f = r.getData(); //Getting the file object of the node
        if (f.isFile()) { //If its a file
            if (f.getName().equals(name)) { // If the short name of the file is the same as the parameter
                return f.getLongName();
            }
        } else { //Recursive case
            Iterator<NLNode<FileObject>> iter = r.getChildren(); //Looping through the children of the node if its a file

            while (iter.hasNext()) { //Looping through the iterator
                NLNode<FileObject> n = iter.next();
                if (n != null) {
                    if (n.getData().getName().equals(name)) {
                        return n.getData().getLongName();
                    }
                }
                findFileTraversal(n, name);
            }
        }
        return "";
    }


    public NLNode<FileObject> getRoot() {
        return root;
    }




}
