<template>
<div>
  用户列表
  <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>权限管理</el-breadcrumb-item>
    <el-breadcrumb-item>用户列表</el-breadcrumb-item>
  </el-breadcrumb>
  <!-- 用户列表主体部分 -->
  <el-card class="card">
    <!--搜索区域-->
    <el-row :gutter="25">
      <el-col :span="10">
        <!--v-model="queryInfo.query" clearable @clear="getUserList"-->
        <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList">
          <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
      </el-col>
    </el-row>
    <!--绘制表格-->
    <el-table class="table" :data="userList" border stripe>
    <!-- 索引列    -->
      <el-table-column type="index"></el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="密码" prop="password"></el-table-column>
      <el-table-column label="角色" prop="role"></el-table-column>
      <el-table-column label="状态" prop="state">
        <!--    作用域插槽    -->
        <template slot-scope="scope">
          <!--    {{scope.row}}  每一行封存的数据    -->
          <el-switch v-model="scope.row.state" @change="userStateChanged(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <!--   修改   @click="showEditDialog(scope.row.id) "  -->
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id) "></el-button>
          <!--   删除  @click="deleteUser(scope.row.id) "   -->
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.id)" ></el-button>
          <!--   权限      -->
          <!--  文字提示  enterable  隐藏    -->
          <el-tooltip effect="dark" content="分配权限" placement="top-start" :enterable="false">
            <el-button type="warning" icon="el-icon-setting" size="mini" ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!-- elementUi分页   -->
    <!--  size-change每页最大变化  -->
    <!-- current-change当前最大变化   -->
    <!-- layout 功能组件   -->
    <div>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNum"
          :page-sizes="[1, 2, 5, 100]"
          :page-size="queryInfo.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <!--   用户名       -->
          <el-form-item label="用户名" prop="username">
            <el-input v-model="addForm.username"></el-input>
          </el-form-item>
          <!--   密码       -->
          <el-form-item label="密码" prop="password">
            <el-input v-model="addForm.password"></el-input>
          </el-form-item>
          <!--    邮箱      -->
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="addForm.email"></el-input>
          </el-form-item>
        </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

   <!-- 修改对话框   -->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!--   用户名       -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <!--   密码       -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <!--    邮箱      -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>
  </el-card>
</div>
</template>

<script>
export default {
  name: "UserList",
  data(){
    return{
      queryInfo:{
        query:"", //查询信息
        pageNum: 1,//当前页
        pageSize: 5,//每页最大数
      },
      userList:[],
      total: 0,//总记录数
      addDialogVisible:false, //对话框状态
      addForm:{
        username:"",
        password:"",
        email:"",
      },
      //修改用户信息
      editForm:{

      },
      // 显示/隐藏修改用户栏
      editDialogVisible:false,
      //表单验证
      addFormRules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 5, max: 8, message: '长度在 5 到 8 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {min: 5, max: 15, message: '请正确输入邮箱', trigger: 'blur'}
        ],
      },
      //表单验证
      editFormRules: {
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {min: 6, max: 8, message: '长度在 6 到 8 个字符', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {min: 5, max: 15, message: '请正确输入邮箱', trigger: 'blur'}
        ],
      }

    }
  },
  created() {
    this.getUserList();
  },
  methods:{
    //获取所有用户
    getUserList(){
      this.$axios.get("/allUser", {
        params:this.queryInfo
      }).then((res) => {
        console.log(res)
        this.userList = res.data.data;
        this.total = res.data.numbers;
      })
    },

    //最大数
    handleSizeChange(newSize){
      this.queryInfo.pageSize = newSize;
      this.getUserList();
    },

    // pageNum的触发动作
    handleCurrentChange(newPage){
      this.queryInfo.pageNum = newPage;
      this.getUserList();
    },
    userStateChanged(userInfo){
      this.$axios.post('/userState'+'?id='+userInfo.id+'&state='+userInfo.state)
          .then((res)=>{
            console.log(res)
            if (res.data != "success"){
              userInfo.id = !userInfo.id
              return this.$message.error("操作失败！！！")
            }
            this.$message.success("操作成功！！！");
          })
    },
    //监听添加用户
    addDialogClosed(){
      this.$refs.addFormRef.resetFields();
    },
    addUser(){
      this.$refs.addFormRef.validate(valid =>{
            console.log(valid)
        if (!valid) return;
        this.$axios.post("/addUser",this.addForm).then((res) =>{
          if (res.data != "success"){
            return this.$message.error("操作失败！！！")
          }
          this.addDialogVisible = false;
          this.getUserList();
          return this.$message.success("操作成功！！！");


        })
      });
    },
    //根据Id删除用户
    async deleteUser(id){
        const confirmResult = await this.$confirm("此操作将永久删除用户，是否继续？","提示",{
        confirmButtonText:"确定",
        cancelButtonText:"取消",
        type:"warning"
      }).catch((err)=>err)
      if(confirmResult !== 'confirm'){
        console.log(confirmResult)
        return this.$message.info("已取消删除")
      }
      this.$axios.delete("/deleteUser?id="+id).then((res)=>{
        if (res.data != "success"){
          return this.$message.error("删除失败！！！");
        }
        this.getUserList();
        return this.$message.success("删除成功！！！");
      })
    },
    //显示对话框
    showEditDialog(id){
      this.$axios.get("/getUpdate?id="+id).then((res)=>{
        this.editForm = res.data;//查询出用户信息
        this.editDialogVisible = true;
      })
    },
    editDialogVisible(){
      this.$refs.editFormRef.resetFields();
    },
    editUserInfo(){
      this.$refs.editFormRef.validate(valid =>{
        console.log(valid)
        if (!valid) return;
        this.$axios.post("/editUser",this.editForm).then((res) =>{
          if (res.data != "success"){
            return this.$message.error("修改失败！！！")
          }
          this.editDialogVisible = false;
          this.getUserList();
          return this.$message.success("修改成功！！！");


        })
      });
    }

  }
}
</script>

<style scoped>
.breadcrumb{
  margin-bottom: 15px;
  font-size: 12px;
}

.card{
  box-shadow: 0 1px 1px rgba(0,8,10,0) !important;
}

.table{
  margin-top: 15px;
  font-size: 12px;
  min-width: 350px;
}

</style>
