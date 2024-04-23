<template>
    <div>
      <h1>Submitted to analysis queue</h1>
      <!-- <p>Please wait with patience.</p> -->
      <div class="text-container">
        <div class="text-content">
          <el-text tag="b" class="mx-1" size="large">No: {{ sessionId }} </el-text><br/>
          <el-text tag="b" class="mx-1" size="large">Submitter: {{ submitter }}</el-text><br/>
          <el-text tag="b" class="mx-1" size="large">Submission Time:{{ submissionTime }} </el-text><br/>
          <el-text tag="b" class="mx-1" size="large">Current Time: {{ currentTime }}</el-text><br/>
          <el-text tag="b" class="mx-1" size="large">Status: {{ currentStatus }}</el-text>
        </div>
      </div>
    </div>
    <div class="demo-progress">
      <div class="parent">
        <el-progress type="circle" :percentage="uploadProgress" :color="changeProgressColor(uploadProgress)" :status="uploadStatus"/>
        <p><el-text class="mx-1" size="medium" type="Default">Upload file to Server</el-text></p>
      </div>
      <div class="parent">
        <el-progress type="circle" :percentage="parseProgress" :color="changeProgressColor(parseProgress)" :status="parseStatus"/>
        <p><el-text class="mx-1" size="medium" type="Default">Parsing JSON file</el-text></p>
      </div>
      <div class="parent">
        <el-progress type="circle" :percentage="analyseProgress" :color="changeProgressColor(analyseProgress)" :status="analyseStatus"/>
        <p><el-text class="mx-1" size="medium" type="Default">Analysing behavioral data</el-text></p>
      </div>
      <div class="parent">
        <el-progress type="circle" :percentage="ratingProgress" :color="changeProgressColor(ratingProgress)" :status="ratingStatus"/>
        <p><el-text class="mx-1" size="medium" type="Default">Getting rating results</el-text></p>
      </div>
      <div class="parent">
        <el-progress type="circle" :percentage="reportProgress" :color="changeProgressColor(reportProgress)" :status="reportStatus"/>
        <p><el-text class="mx-1" size="medium" type="Default">Generating ux rate report</el-text></p>
      </div>
    </div>
</template>

<script>
import { Check } from '@element-plus/icons-vue'
import { ref, onMounted, watch, watchMounted } from 'vue';
import axios from 'axios';
import { useRoute } from "vue-router"
import router from '@/router';

export default {
  name: 'Process',
  data() {
    return {
      uploadProgress: 0,
      parseProgress: 0,
      analyseProgress: 0,
      ratingProgress: 0,
      reportProgress: 0,
      sessionId: '',
      submitter: '',
      submissionTime: '',
      currentStatus: '',
      currentTime: '',
      statusList: ['Waiting upload', 'Waiting analysis'],
      uploadStatus: '',
      parseStatus: '',
      analyseStatus: '',
      ratingStatus: '',
      reportStatus: ''
    }
  },
  mounted() {
    this.sessionId = this.$route.query.sessionId
    this.initWebSocket()
    this.submitter = window.navigator.userAgent.split(') ')[2]
    this.submissionTime = this.$route.query.submissionTime.replace(/\+/g, '')
    this.getNowTime();
    clearInterval(myTimeDisplay );
    var myTimeDisplay = setInterval(() => {
        this.getNowTime(); 
    }, 1000);
  },  
  created() {

  },
  methods: {
    initWebSocket() {
      var url = 'ws://localhost:8080' + "/websocket/" + this.sessionId;
      this.websock = new WebSocket(url);
      this.websock.onopen = this.websocketonopen;
      // this.websock.send = this.websocketsend;
      this.websock.onerror = this.websocketonerror;
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onclose = this.websocketclose;
    },
    // 连接成功后调用
    websocketonopen: function () {
      console.log("WebSocket连接成功");
    },
    // 发生错误时调用
    websocketonerror: function (e) {
      console.log(e)
      console.log("WebSocket连接发生错误");
    },
    // 给后端发消息时调用
    // websocketsend: function (message) {
    //   this.websock.send(message)
    // },
    // 接收后端消息
    // vue 客户端根据返回的cmd类型处理不同的业务响应
    websocketonmessage: function (e) {
      let jsonData = JSON.parse(e.data)
      let type = jsonData.type
      let progress = jsonData.progress

      if (type === 'upload') {
        this.uploadProgress = Math.floor(progress)
        this.currentStatus = 'Upload file to Server'
        if (this.uploadProgress === 100) {
          this.uploadStatus = 'success'
          let message = { 'type': 'parse', 'sessionId': this.sessionId } 
          message = JSON.stringify(message)
          this.websock.send(message)
        }
      }else if (type === 'parse') {
        this.parseProgress = Math.floor(progress)
        this.currentStatus = 'Parsing JSON file'
        if (this.parseProgress === 100) {
          this.parseStatus = 'success'
          let message = { 'type': 'analyse', 'sessionId': this.sessionId } 
          message = JSON.stringify(message)
          this.websock.send(message)
        }
      }else if (type === 'analyse') {
        this.analyseProgress = Math.floor(progress)
        this.currentStatus = 'Analysing behavioral data'
        if (this.analyseProgress === 100) {
          this.analyseStatus = 'success'
          let message = { 'type': 'rating', 'sessionId': this.sessionId } 
          message = JSON.stringify(message)
          this.websock.send(message)
        }
      }else if (type === 'rating') {
        this.ratingProgress = Math.floor(progress)
        this.currentStatus = 'Getting rating results'
        if (this.ratingProgress === 100) {
          this.ratingStatus = 'success'
          let message = { 'type': 'report', 'sessionId': this.sessionId } 
          message = JSON.stringify(message)
          this.websock.send(message)
        }
      }else if (type === 'report') {
        this.reportProgress = Math.floor(progress)
        this.currentStatus = 'Generating ux rate report'
        if (this.reportProgress === 100) {
          this.reportStatus = 'success'
          this.$message.success({
            duration: 5000, 
            message: "Redirecting to report rating page..."
          })
          setTimeout(() => {
            router.push({
              path: 'report',
              query: { 
                sessionId: this.sessionId
              }
            })
          }, 3000)
        }
      }
    },
    // 关闭连接时调用
    websocketclose: function (e) {
      console.log("connection closed (" + e.code + ")");
    },
    getNowTime() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hours = String(currentDate.getHours()).padStart(2, '0');
      const minutes = String(currentDate.getMinutes()).padStart(2, '0');
      const seconds = String(currentDate.getSeconds()).padStart(2, '0');

      const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      this.currentTime = formattedTime;
    },
    destroyed: function () { // 离开页面生命周期函数
      this.websocketclose();
    },
    changeProgressColor(progress) {
      if (progress < 30) {
        return '#909399'
      }
      if (progress < 70) {
        return '#e6a23c'
      }
      return '#67c23a'
    }
  }
}

</script>

<style scoped>
.percentage-value {
  display: block;
  margin-top: 10px;
  font-size: 28px;
}
.percentage-label {
  display: block;
  margin-top: 10px;
  font-size: 12px;
}
.text-container{
  position: relative;
  margin-left: 40%;
}
.text-content {
  text-align: left
}
.demo-progress .el-progress--circle {
  margin-right: 15px;
  top: 40px;
}
.demo-progress p {
  display: inline-block;
  vertical-align: top;
}
.parent {
  display: inline-block;
}
.parent p {
  position: relative;
  top: 180px; 
  left: -108px; 
  transform: translate(-30%, -50%);
}
</style>