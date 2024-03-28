<%-- 
    Document   : register
    Created on : Mar 1, 2024, 9:40:52 PM
    Author     : phamx
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/register.css">
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <!-- end header -->

            <div class="form-information">
                <form action="MemberController" method="post" class="form-register">
                    <h2>Register Information</h2>

                    <p>Personal Information</p>
                    <div class="row-1">

                        <div class="lastName">
                            <label for="">
                                Last Name
                            </label>
                            <br>
                            <input class="customInput" type="text" placeholder="Your last name" name="lastname" value="${member.lastname}" required>
                        </div>

                        <div class="firstName">
                            <label for="">
                                First Name
                            </label>
                            <br>
                            <input class="customInput" type="text" placeholder="Your first name" name="firstname" value="${member.firstname}" required>
                        </div>

                        <div class="dateOfBirth">
                            <label for="">
                                Date Of Birth
                            </label>
                            <br>
                            <input class="customInput" type="date" name="birthday" value="${member.birthday}" required>
                            <div class="errorMsg">${errorBirthday}</div>
                        </div>

                        <div class="gender">
                            <label for="">
                                Gender
                            </label>
                            <br>
                            <select class="customInput" name="gender" required>
                                <option value="" selected disabled hidden>Choose your gender</option>
                                <option value="Female" ${member.gender eq 'Female'? 'selected' : ''} >Female</option>
                                <option value="Male" ${member.gender eq 'Male'? 'selected' : ''} >Male</option>
                                <option value="Other" ${member.gender eq 'Other'? 'selected' : ''} >Other</option>
                            </select>
                        </div>
                    </div>

                    <p>Contact Information</p>
                    <div class="row-2">
                        <div class="email">
                            <label for="">
                                Email
                            </label>
                            <br>
                            <input class="customInput" type="email" placeholder="Your email here" name="email" value="${member.email}" required>
                            <div class="errorMsg">${errorEmail}</div>
                        </div>
                        <div class="phoneNumber">
                            <label for="">
                                Phone Number
                            </label>
                            <br>
                            <input class="customInput" type="text" placeholder="Your phone number" name="phoneno" value="${member.phoneno}" required>
                        </div>                        
                        <div class="nationality">                            
                            <label for="">
                                Nationality
                            </label>
                            <br>
                            <select class="customInput addWidth" name="nationality" required>
                                <option value="" selected disabled hidden>Your nationality</option>
                                <c:forTokens var="nation" items="Afghanistan,Albania,Algeria,American Samoa,Andorra,Angola,Anguilla,Antarctica,Antigua and Barbuda,Argentina,Armenia,Aruba,Australia,Austria,Azerbaijan,Bahamas,Bahrain,Bangladesh,Barbados,Belarus,Belgium,Belize,Benin,Bermuda,Bhutan,Bolivia,Bonaire,Bosnia and Herzegovina,Botswana,Bouvet Island,Brazil,British Indian Ocean Territory,Brunei Darussalam,Bulgaria,Burkina Faso,Burundi,Cabo Verde,Cambodia,Cameroon,Canada,Cayman Islands,Central African Republic,Chad,Chile,China,Christmas Island,Cocos Islands,Colombia,Comoros,Congo,Cook Islands,Costa Rica,Croatia,Cuba,Curacao,Cyprus,Czechia,Denmark,Djibouti,Dominica,Dominican Republic,East Timor,Ecuador,Egypt,El Salvador,Equatorial Guinea,Eritrea,Estonia,Eswatini,Ethiopia,Falkland Islands,Faroe Islands,Fiji,Finland,France,French Guiana,French Polynesia,French Southern Territories,Gabon,Gambia,Georgia,Germany,Ghana,Gibraltar,Greece,Greenland,Grenada,Guadeloupe,Guam,Guatemala,Guernsey,Guinea,Guinea-Bissau,Guyana,Haiti,Heard Island and McDonald Islands,Holy See,Honduras,Hong Kong,Hungary,Iceland,India,Indonesia,Iran,Iraq,Ireland,Isle of Man,Israel,Italy,Jamaica,Japan,Jersey,Jordan,Kazakhstan,Kenya,Kiribati,Korea,Kuwait,Kyrgyzstan,Lao People's Democratic Republic,Latvia,Lebanon,Lesotho,Liberia,Libya,Liechtenstein,Lithuania,Luxembourg,Macao,Madagascar,Malawi,Malaysia,Maldives,Mali,Malta,Marshall Islands,Martinique,Mauritania,Mauritius,Mayotte,Mexico,Micronesia,Moldova,Monaco,Mongolia,Montenegro,Montserrat,Morocco,Mozambique,Myanmar,Namibia,Nauru,Nepal,Netherlands,NETHERLANDS ANTILLES,New Caledonia,New Zealand,Nicaragua,Niger,Nigeria,Niue,Norfolk Island,North Macedonia,Northern Mariana Islands,Norway,Oman,Pakistan,Palau,Palestine,Panama,Papua New Guinea,Paraguay,Peru,Philippines,PitCairn,Poland,Portugal,Puerto Rico,Qatar,Romania,Russia Federation,Rwanda,Reunion,Saint Barthelemy,Saint Helena,Saint Kitts and Nevis,Saint Lucia,Saint Martin,Saint Pierre and Miquelon,Saint Vincent and the Grenadines,Samoa,San Marino,Sao Tome and Principe,Saudi Arabia,Senegal,Serbia,Seychelles,Sierra Leone,Singapore,Sint Maarten,Slovakia,Slovenia,Solomon Islands,Somalia,South Africa,South Georgia and the South Sandwich Islands,South Sudan,Spain,Sri Lanka,Sudan,Suriname,Svalbard,Sweden,Switzerland,Syrian Arab Republic,Taiwan,Tajikistan,Tanzania,Thailand,Timor-Leste,Togo,Tokelau,Tonga,Trinidad and Tobago,Tunisia,Turkey,Turkmenistan,Turks and Caicos Islands,Tuvalu,Uganda,Ukraine,United Arab Emirates,United Kingdom of Great Britain and Northern Ireland,United States Minor Outlying Islands,United States of America,Uruguay,Uzbekistan,Vanuatu,Venezuela,Viet Nam,Virgin Islands (British),Virgin Islands (U.S),Wallis and Futuna,Western Sahara,Yemen,Zambia,Zimbabwe" delims=",">
                                    <option value="${nation}" ${member.nationality eq nation ? 'selected' :''} >${nation}</option>
                                </c:forTokens>  
                            </select>
                        </div>
                        <div class="idNumber">
                            <label for="">
                                ID Number
                            </label>
                            <br>
                            <input class="customInput" type="text" placeholder="Your ID number" name="numberID" value="${member.numberID}" required>
                        </div>
                    </div>

                    <p>Account Information</p>
                    <div class="row-3">
                        <div class="password">
                            <label for="">
                                Password
                            </label>
                            <br>
                            <input class="customInput" id="pw" type="password" placeholder="Your password" name="password" required>
                        </div>
                        <div class="confirmPassword">
                            <label for="">
                                Confirm Password
                            </label>
                            <br>
                            <input class="customInput" id="cfpw" type="password" placeholder="Confirm your password" required>
                        </div>
                    </div>                    

                    <div class="row-4">
                        <button type="submit" name="action" value="Register">Register</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            const password = document.getElementById("pw");
            const confirmPassword = document.getElementById("cfpw");

            function validatePassword() {
                if (password.value !== confirmPassword.value) {
                    confirmPassword.setCustomValidity("Passwords Don't Match");
                } else {
                    confirmPassword.setCustomValidity('');
                }
            }

            password.onchange = validatePassword;
            confirmPassword.onkeyup = validatePassword;
        </script>
    </body>

</html>