# How run application in docker


# Default users in application

1) username: qwer1111 pw: 1234 role: ADMIN 
2) username: qwer2222 pw: 1234 role: --
3) username: qwer3333 pw: 1234 role: ACCOUNTANT


# New user creating
 
Only admin can create new users. When a user is created, an alphanumeric code is generated, which is used as a username,
and a four-digit pin, which is used as a password. This data is sent to the user's email (if the "Send email" option is checked).
The password can be changed by the administrator in user editing page.

