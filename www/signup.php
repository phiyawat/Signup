<?php
require_once("connect.php");
$username = $_POST['username'];
$password = $_POST['password'];
$firstname = $_POST['firstname'];
$lastname = $_POST['lastname'];
$email = $_POST['email'];
$sql = "INSERT INTO user(username,password,firstname,lastname,email)";
$sql .= "VALUES('$username','$password','$firstname','$lastname','$email')";
$result = mysql_query($sql,$link);
if($result){
    echo "OK";
}
else{
    echo "Fail";
}
?>
