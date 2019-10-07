<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 2019-09-19
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap-3.3.7.css"/>
    <script type="text/javascript" src="/static/js/vue/vue-2.4.0.js"></script>
    <script type="text/javascript" src="/static/js/vue/vue-router-3.0.1.js"></script>
    <script type="text/javascript" src="/static/js/vue/vue-resource-1.3.4.js"></script>
</head>
<body>

<div id="app">
    <div class="form-group" style="text-align: center;font-size: 35px;" >{{ title }}</div>
    <com1 @submit="submit"></com1>
</div>

<template id="com1">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="username" placeholder="请输入用户名" v-model="username">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="password" placeholder="请输入密码" v-model="password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox">请记住我
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-default" @click.prevent="btnCLick">登录</button>
            </div>
        </div>
    </form>
</template>

</body>
<script>

    var com1 = {
        template: '#com1',
        data(){
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            btnCLick(){
                console.log(this.username + "  :  " +this.password);
                this.$emit('submit',this.username,this.password);
            }
        }
    };

    var vue = new Vue({
        el: '#app',
        data(){
            return {
                title: '登录页面',
            }
        },
        methods:{
            submit(username,password) {
                console.log(username+'='+password);
                this.$http.post('/doLogin',{'username':username, 'password': password}, {emulateJSON: true}).then(function (result) {
                    console.log(result.bodyText);
                    if (result.bodyText == 200){
                        location.href = '/jsp';
                    }
                });
            }
        },
        components: {
            'com1': com1
        }
    });
</script>
</html>
