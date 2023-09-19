<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="Connection.dbconnection"%>
<%
    String KEY = request.getParameter("requestType").trim();
    System.out.println("Key : " + KEY);
    dbconnection dbobj = new dbconnection();
    if (KEY.equals("login")) {
        System.out.println("yess");
        String name = request.getParameter("U_name");
        String pass = request.getParameter("P_swd");
        String qry = "select regid,type from login where name='" + name + "'and email='" + pass + "' AND status='1'";
        Iterator itr = dbobj.getData(qry).iterator();
        if (itr.hasNext()) {
            Vector v = (Vector) itr.next();
            String res = v.get(0) + "#" + v.get(1);
            out.print(res);
            System.out.print(res);
        } else {
            out.print("failed");
            System.out.print("no");
        }
    }

    //user register
    if (KEY.equals("singlegroup")) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phon = request.getParameter("phone");
        String pass = request.getParameter("pass");
        String add = request.getParameter("add");

        String qry = "INSERT INTO `user`(`name`,`email`,`phone`,`pass`,address,type)VALUES('" + name + "','" + email + "','" + phon + "','" + pass + "','" + add + "','suser')";
        String qry1login = "insert into login (regid,name,email,type,status)values((select max(uid)from user),'" + email + "','" + pass + "','user','1')";

        int rs = dbobj.putData(qry);

        int rss = dbobj.putData(qry1login);
        if (rs > 0 && rss > 0) {
        } else {
            out.print("failed");
            System.out.print("failed");
            out.print("sucess");
            System.out.print("sucess");
        }
    }

//
    //user register
    if (KEY.equals("groupuser")) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phon = request.getParameter("phone");
        String pass = request.getParameter("pass");
        String add = request.getParameter("add");

        String qry = "INSERT INTO `user`(`name`,`email`,`phone`,`pass`,address,type)VALUES('" + name + "','" + email + "','" + phon + "','" + pass + "','" + add + "','guser')";
        String qry1login = "insert into login (regid,name,email,type,status)values((select max(uid)from user),'" + email + "','" + pass + "','user','1')";

        int rs = dbobj.putData(qry);
        int rss = dbobj.putData(qry1login);
        if (rs > 0 && rss > 0) {
        } else {
            out.print("failed");
            System.out.print("failed");
            out.print("sucess");
            System.out.print("sucess");
        }
    }

    //
    if (KEY.equals("getdonatorlist")) {
        JSONArray array = new JSONArray();
        String qry = "SELECT * FROM `user`";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(0).toString().trim());
                obj.put("name", v.get(1).toString().trim());
                obj.put("email", v.get(2).toString().trim());
                obj.put("phone", v.get(3).toString().trim());
                obj.put("address", v.get(4).toString().trim());
                obj.put("pass", v.get(5).toString().trim());
                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//reporting
    if (KEY.equals("Reporting")) {
        String shopid = request.getParameter("uid");
        String name = request.getParameter("name");
        String img = request.getParameter("image");
        String des = request.getParameter("Description");
        String status = "No Action";
        System.out.print(shopid);
        String addcake = "insert into compliant(userid,image,name,des,report)values('" + shopid + "','" + img + "','" + name + "','" + des + "','" + status + "')";
        int rs = dbobj.putData(addcake);
        if (rs > 0) {
            System.out.print("sucess");
            out.print("sucess");
        } else {
            System.out.print("failed");
            out.print("failed");

        }
    }
    //
    if (KEY.equals("Feedback")) {
        String name = request.getParameter("uid");
        String nam = request.getParameter("fname");
        String fed = request.getParameter("fdes");
        String rate = request.getParameter("star");
        String qry = "insert into feedback(uid,fname,fdes,rate)values('" + name + "','" + nam + "','" + fed + "','" + rate + "')";
        System.out.print(qry);
        int rs = dbobj.putData(qry);
        if (rs > 0) {
            out.print("sucess");
            System.out.print("sucess");
        } else {
            out.print("failed");
            System.out.print("failed");

        }
    }

//
    if (KEY.equals("getcomplaints")) {
        JSONArray array = new JSONArray();
        String qry = "SELECT `user`.`name`,`user`.`email`,`compliant`.* FROM `compliant`,`user` WHERE `user`.`uid`=`compliant`.`userid`";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(2).toString().trim());
                obj.put("name", v.get(0).toString().trim());
                obj.put("email", v.get(1).toString().trim());
                obj.put("phone", v.get(6).toString().trim());
                obj.put("pass", v.get(7).toString().trim());
                obj.put("img", v.get(4).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//
    if (KEY.equals("Blockreporting")) {
        String user = request.getParameter("cid");
        System.out.print(user);
        String status = "ActionCompleted";
        String up = "UPDATE compliant SET report='" + status + "' where cid='" + user + "'";
        System.out.print(user + "   " + up);

        int rss = dbobj.putData(up);
        if (rss > 0) {
            out.print("sucess");
            System.out.print("Sucees");
        } else {
            out.print("faield");
            System.out.print("faield");
        }
    }
    //
    if (KEY.equals("getallreviews")) {
        JSONArray array = new JSONArray();
        String qry = "SELECT *FROM `feedback`";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(2).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("pass", v.get(4).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

    //
    if (KEY.equals("addfooditems")) {
        System.out.println("addnike inside");
        String uid = request.getParameter("uid");
        String brandname = request.getParameter("brandname");
        String details = request.getParameter("details");
        String insurance = request.getParameter("insurance");
        String address = request.getParameter("address");
        String latt = request.getParameter("latt");
        String longg = request.getParameter("longg");
        String image = request.getParameter("image");
        String amount = request.getParameter("amount");

        String addcake = "INSERT INTO `addfood`(`uid`,`bname`,`details`,`qantity`,`address`,`latt`,`longg`,`image`,`type`)values"
                + "('" + uid + "','" + brandname + "','" + details + "','" + insurance + "','" + address + "','" + latt
                + "','" + longg + "','" + image + "','" + amount + "')";
        int rs = dbobj.putData(addcake);
        if (rs > 0) {
            System.out.print("sucess");
            out.print("sucess");
        } else {
            System.out.print("failed");
            out.print("failed");

        }
    }
//

    if (KEY.equals("adddonations")) {
        System.out.println("addnike inside");
        String uid = request.getParameter("uid");
        String brandname = request.getParameter("brandname");
        String details = request.getParameter("details");
        String insurance = request.getParameter("insurance");
        String address = request.getParameter("address");
        String latt = request.getParameter("latt");
        String longg = request.getParameter("longg");
        String image = request.getParameter("image");

        String addcake = "INSERT INTO `adddonations`(`uid`,`dname`,`details`,`qantity`,`address`,`latt`,`longg`,`image`)values"
                + "('" + uid + "','" + brandname + "','" + details + "','" + insurance + "','" + address + "','" + latt
                + "','" + longg + "','" + image + "')";
        int rs = dbobj.putData(addcake);
        if (rs > 0) {
            System.out.print("sucess");
            out.print("sucess");
        } else {
            System.out.print("failed");
            out.print("failed");
        }
    }

//
    if (KEY.equals("GetallnamesList")) {
        String id=request.getParameter("id");
        JSONArray array = new JSONArray();
        String qry = "SELECT `adddonations`.* ,`user`.`phone` FROM `user`,`adddonations` WHERE `adddonations`.`uid`=`user`.`uid` and `adddonations`.uid!='"+id+"'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("dntid", v.get(0).toString().trim());
                obj.put("uid", v.get(1).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("detail", v.get(3).toString().trim());
                obj.put("qty", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("lat", v.get(6).toString().trim());
                obj.put("lot", v.get(7).toString().trim());
                obj.put("img", v.get(8).toString().trim());
                obj.put("phone", v.get(9).toString().trim());
                array.add(obj);
            }
            out.println(array);
            System.out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//
    if (KEY.equals("sendrequestTo")) {
        String u_id = request.getParameter("u_id");
        String did = request.getParameter("dnid");

        String up = "INSERT INTO `request`(`uid`,`dnid`,`status`,`type`)VALUES('" + u_id + "','" + did + "','no','item')";
        System.out.print(up);

        int rss = dbobj.putData(up);
        if (rss > 0) {
            out.print("sucess");
            System.out.print("Sucees");
        } else {
            out.print("faield");
            System.out.print("faield");
        }
    }
//

    if (KEY.equals("getfooddetails")) {
        JSONArray array = new JSONArray();
                String id=request.getParameter("id");

        String qry = "SELECT `addfood`.* ,`user`.`phone` FROM `user`,`addfood` WHERE `addfood`.`uid`=`user`.`uid` and `addfood`.uid!='"+id+"' ";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("dntid", v.get(0).toString().trim());
                obj.put("uid", v.get(1).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("detail", v.get(3).toString().trim());
                obj.put("qty", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("lat", v.get(6).toString().trim());
                obj.put("lot", v.get(7).toString().trim());
                obj.put("img", v.get(8).toString().trim());
                obj.put("phone", v.get(9).toString().trim());
                array.add(obj);
            }
            out.println(array);
            System.out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
    if (KEY.equals("foodrequest")) {
        String u_id = request.getParameter("u_id");
        String did = request.getParameter("dnid");

        String up = "INSERT INTO `request`(`uid`,`dnid`,`status`,`type`)VALUES('" + u_id + "','" + did + "','no','food')";
        System.out.print(up);

        int rss = dbobj.putData(up);
        if (rss > 0) {
            out.print("sucess");
            System.out.print("Sucees");
        } else {
            out.print("faield");
            System.out.print("faield");
        }
    }

//
//myreqyest
    if (KEY.equals("myreqyest")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("id");
        String qry = "SELECT `adddonations`.*,`request`.rid,`request`.`status` FROM `adddonations`,`request` WHERE `adddonations`.`did`=`request`.`dnid` AND `request`.`type`='item' AND `request`.uid='" + uid + "'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(9).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(10).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//new request
    if (KEY.equals("New_request")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("id");
        String qry = "SELECT `adddonations`.*,`request`.rid,`request`.`status` FROM `adddonations`,`request` WHERE `adddonations`.`did`=`request`.`dnid` AND `adddonations`.`uid`='" + uid + "' AND `request`.`type`='item'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(9).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(10).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
    if (KEY.equals("updatedProductItems")) {
        String user = request.getParameter("rid");
        System.out.print(user);
        String status = "yes";
        String up = "UPDATE request SET status='" + status + "' where rid='" + user + "' and type='item'";
        System.out.print(user + "   " + up);

        int rss = dbobj.putData(up);
        if (rss > 0) {
            out.print("sucess");
            System.out.print("Sucees");
        } else {
            out.print("faield");
            System.out.print("faield");
        }
    }

///////////////////////////////////////////////////////////////
//Foodmyreqyest
    if (KEY.equals("Foodmyreqyest")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("id");
        String qry = "SELECT `addfood`.*,`request`.rid,`request`.`status` FROM `addfood`,`request` WHERE `addfood`.`foodid`=`request`.`dnid` AND `request`.`type`='food' AND `request`.uid='" + uid + "'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(10).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(11).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//new request
    if (KEY.equals("FoodNew_request")) {
        JSONArray array = new JSONArray();
        String uid = request.getParameter("id");
        String qry = "SELECT `addfood`.*,`request`.rid,`request`.`status` FROM `addfood`,`request` WHERE `addfood`.`foodid`=`request`.`dnid` AND `addfood`.`uid`='" + uid + "' AND `request`.`type`='food'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(10).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(11).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }
    if (KEY.equals("foodupdatedProductItems")) {
        String user = request.getParameter("rid");
        System.out.print(user);
        String status = "yes";
        String up = "UPDATE request SET status='" + status + "' where rid='" + user + "' and type='food'";
        System.out.print(user + "   " + up);

        int rss = dbobj.putData(up);
        if (rss > 0) {
            out.print("sucess");
            System.out.print("Sucees");
        } else {
            out.print("faield");
            System.out.print("faield");
        }
    }
    //////////////////////admin

    if (KEY.equals("foodrequest_Admin")) {
       
        JSONArray array = new JSONArray();
        String qry = "SELECT `addfood`.*,`request`.rid,`request`.`status` FROM `addfood`,`request` WHERE `addfood`.`foodid`=`request`.`dnid` AND `request`.`type`='food'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(9).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(11).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//2
    if (KEY.equals("donationrequestAdmin")) {
        JSONArray array = new JSONArray();
        String qry = "SELECT `adddonations`.*,`request`.rid,`request`.`status` FROM `adddonations`,`request` WHERE `adddonations`.`did`=`request`.`dnid` AND `request`.`type`='item'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {

            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("uid", v.get(9).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("email", v.get(3).toString().trim());
                obj.put("phone", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("pass", v.get(10).toString().trim());

                array.add(obj);
            }
            out.println(array);
        } else {
            System.out.println("else id");
            out.print("failed");
        }
    }

//seraching//////////////////////////////////////////////////////////////
    if (KEY.equals("SearchDonation")) {
        JSONArray array = new JSONArray();
        String ser = request.getParameter("search");
        String qry = "SELECT `adddonations`.* ,`user`.`phone` FROM `user`,`adddonations` WHERE `adddonations`.`uid`=`user`.`uid` and `adddonations`.`dname`='" + ser + "'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("dntid", v.get(0).toString().trim());
                obj.put("uid", v.get(1).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("detail", v.get(3).toString().trim());
                obj.put("qty", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("lat", v.get(6).toString().trim());
                obj.put("lot", v.get(7).toString().trim());
                obj.put("img", v.get(8).toString().trim());
                obj.put("phone", v.get(9).toString().trim());
                array.add(obj);
            }
            out.println(array);
            System.out.println(array);
        } else {
            

            System.out.println("else id");
            out.print("failed");
            String items = "INSERT INTO `preference`(`item`,`dates`)VALUES('" + ser + "','" + LocalDate.now() + "')";
            int rss = dbobj.putData(items);
        }
    }

    if (KEY.equals("SearchFood")) {
        JSONArray array = new JSONArray();
        String ser = request.getParameter("search");

        String qry = "SELECT `addfood`.* ,`user`.`phone` FROM `user`,`addfood` WHERE `addfood`.`uid`=`user`.`uid` and `addfood`.`bname`='" + ser + "'";
        System.out.println("qry=" + qry);
        Iterator it = dbobj.getData(qry).iterator();
        if (it.hasNext()) {
            while (it.hasNext()) {
                Vector v = (Vector) it.next();
                JSONObject obj = new JSONObject();
                obj.put("dntid", v.get(0).toString().trim());
                obj.put("uid", v.get(1).toString().trim());
                obj.put("name", v.get(2).toString().trim());
                obj.put("detail", v.get(3).toString().trim());
                obj.put("qty", v.get(4).toString().trim());
                obj.put("address", v.get(5).toString().trim());
                obj.put("lat", v.get(6).toString().trim());
                obj.put("lot", v.get(7).toString().trim());
                obj.put("img", v.get(8).toString().trim());
                obj.put("phone", v.get(9).toString().trim());
                array.add(obj);
            }
            out.println(array);
            System.out.println(array);
        } else {
  
            System.out.println("else id");
            out.print("failed");
                      String items = "INSERT INTO `preference`(`item`,`dates`)VALUES('" + ser + "','" + LocalDate.now() + "')";
            int rss = dbobj.putData(items);
        }
    }
    
//
if (KEY.equals("getpreference")) {
  
        String str = "SELECT COUNT(item),`item` FROM `preference` GROUP BY item";
        Iterator itr = dbobj.getData(str).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("name", v.get(1).toString());
                jsonobj.put("id", v.get(0).toString());
                jsonarray.add(jsonobj);
            }
            out.print(jsonarray);
            System.out.print(jsonarray);
        } else {
            out.println("failed");
        }
    }
%>