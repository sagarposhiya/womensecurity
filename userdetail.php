<?php
$db = "womensecurity";
$U_Id = $_POST["U_Id"];
$Firstname = $_POST["Firstname"];
$Lastname = $_POST["Lastname"];
$Address = $_POST["Address"];
$Mobilenumber = $_POST["Mobilenumber"];
$Email = $_POST["Email"];
$E_number1 = $_POST["E_number1"];
$E_number2 = $_POST["E_number2"];
$E_number3 = $_POST["E_number3"];
$DOB = $_POST["DOB"];
$Password = $_POST["Password"];
$host = "localhost";


$conn = mysqli_connect($host,$Firstname,$Last122name,$Address,$Mobilenumber,$Email,$E_number1,$E_number2,$E_number3,$DOB,$Password,$db);
if($conn)
{
		$q= "select * from tbl_userdetails where U_Id like '$U_Id' and Firstname like '$Firstname' and Lastname like '$Lastname' and 
		Address like '$Address' and Mobilenumber like '$Mobilenumber' and Email like '$Email' and E_number1 like '$E_number1' and E_number2 like '$E_number2' and E_number3 like '$E_number3' 
		and DOB like '$DOB' and Password like '$Password'";
		$result = mysqli_query($conn , $q);
		
		if(mysqli_num_rows($result)>0)
		{
			echo"login successfully...!";
		}
		else
		{
			"Login failed...!";
		}
		else
		{
			echo"Not Connected....!";
		}
}
?>















































+





































































































































































































































































































