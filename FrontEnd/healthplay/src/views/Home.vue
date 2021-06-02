<template>
  <el-container class="home-container">
    <!--头部布局-->
    <el-header class="el-header">
      <div class="el-header-div">
        <img src="../assets/logo.png"  class="logo_img" />
        <span class="el-header-span">运动管理平台</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <el-container>
      <!--侧边布局-->
      <el-aside class="el-aside" :width="isCollapse ?'64px':'200px'">
        <!--伸缩按钮-->
        <div class="toggle-button el-header-div" @click="toggleCollapase">|||</div>
        <!--侧边栏菜单区 unique-opened="true" 只保持一个菜单展开 router开启路由 active-text-color 颜色-->
        <el-menu class="el-menu" background-color="#545c64" text-color="#fff" active-text-color="#409eff" unique-opened :collapse="isCollapse"
                 :collapse-transition="false"  :router="true" :default-active="activePath">
          <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
            <template slot="title">
              <i :class="iconsObject[item.id]"></i>
              <span>{{item.title}}</span>
            </template>
            <el-menu-item  :index="it.path+''" v-for="it in item.slist" :key="it.id"  @click="saveNavState(it.path+'')">
              <template slot="title" >
                <i :class="iconsObject[it.id]"></i>
                <span>{{it.title}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--主体布局-->
      <el-main class="el-main">
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
name: "Home",
  data() {
    return {
      // 左侧菜单
      menuList: [],
      iconsObject: {
        '100':'iconfont1 el-icon-aim',
        '200':'iconfont1 el-icon-aim',
        '101':'iconfont1 el-icon-aim',
        '102':'iconfont1 el-icon-aim',
        '103':'iconfont1 el-icon-aim',
        '104':'iconfont1 el-icon-aim',
        '201':'iconfont1 el-icon-aim',
        '202':'iconfont1 el-icon-aim',
        '203':'iconfont1 el-icon-aim',
        '204':'iconfont1 el-icon-aim',
      },
      isCollapse:false,
      // 被激活的连接
      activePath:'',
    }
  },
  // 类似onload
  created() {
    this.getMenuList();
    this.activePath = window.sessionStorage.getItem('activePath');// 取出session里的访问路径
  },
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/");
    },
    // 获取所有的导航菜单
    async getMenuList(){
      const {data:res} = await this.$axios.get("/menus");
      console.log(res.data);
      if( res.status != 200) return this.$message.error("操作失败！！！");
      this.menuList = res.data;
    },
    // 切换菜单折叠与展开
    toggleCollapase(){
      this.isCollapse = !this.isCollapse;
    },
    // 保存二级菜单的路径
    saveNavState(activePath){
      window.sessionStorage.setItem('activePath',activePath);// 存放点击的二级菜单
      this.activePath = activePath;// 给点击的菜单添加高亮
    },
  }
}
</script>

<style scoped>
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0%;
  align-items: center;
  color: #fff;
  font-size: 20px;
}

.el-header-div {
  display: flex;
  align-items: center;
}


.el-header-span {
  margin-left: 15px;
}


.el-aside {
  background-color: #333744;
  height: 900px;
}
.el-menu{
  border-right: none;
}

.el-main {
  background-color: #eaedf1;
}
.home-container {
  height: 100%;
}

.logo_img {
  width: 20%;
  height: 100%;
}
.iconfont1{
  margin-right: 10px;
}
.toggle-button{
  background-color:#4A5064;
  font-size: 10px;
  line-height: 24px;
  color:#fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
