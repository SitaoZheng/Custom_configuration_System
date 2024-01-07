# Custom Configuration System    
##### CPS 2232 Final Project
 
## Introduction
This program is a program for importing and exporting "user computer accessories configuration list", the program allows users to fill in the configuration information in the program, the export file format for the configuration list.xlsx, while supporting the format file for the configuration list file import into the program, easy to modify the configuration information. The program is equipped with a simple file that records the information of accessories, allowing users to legally add, delete, modify, and query operations.

## About Interfaces
The main interface of the program consists of three tabs.

* Fuction 1
> The main function of the first TAB is to import and export the configuration list. The first column of the table on the left is the name of the Hardware, and the second column is the specific name of the product of the hardware. If the Item data is filled in by automatic filling, the value of the product in the data will be automatically retrieved and displayed in the Value, and the TOTAL will be updated according to the change of all the data in the value. The input box at the bottom is the input box of the configuration list. The user can enter the remark in the box. The top right is the input field of Excel File, which is the name of the file (without extension) when it is exported or imported. Located between the two buttons on the right is the log area, which facilitates customers to interact with the program. The path to both the export and import files is in "src/resource/data/file".

* Function 2
> The main function of the second TAB is to add, delete and modify the specific data of the hardware in the program. The program allows the customer to select the Hardware that needs to be modified (eight types) and then input the Item in the first row to delete the data of the Item, or input the Item and Value in the first row to add the data. To modify the data, the program requires the user to enter the original name in the first row of the Item, the changed name in the second row of the Item, and the changed price in the second row of the Value, so the user does not need to enter anything in the first row of the Value to modify the data. At the bottom left of the whole interface is still a log area, used to inform the user of the status of changes to the data.

* Function 3
> The third TAB allows the user to select Hardware, Item or Value and then enter the data keywords in the following box to further search for all the relevant data information displayed in the bottom left table. The Hardware class performs a case-insensitive search, the Item class performs a case-sensitive search to match the beginning of the entered string, and the Value class performs a search to match the beginning of the entered number (equivalent to a string). There is also a log area on the right.
