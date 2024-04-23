<template>
  <img alt="Vue logo" src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-v3-slim-light.svg">
  <div class="hello">
    <h1>{{ msg }}</h1>
    <div>
      <el-text 
        class="mx-1,text-wrap"
        size="large"
        line-clamp="3">
        We provide online analysis of behavioral data of 
        <el-text class="mx-1" type="success" size="large">user session interactions</el-text>
        on websites.
      </el-text>
      <br/>
      <el-text
        class="mx-1,text-line"
        size="large">
        Please upload your 
        <el-text class="mx-1" type="warning" size="large">JSON</el-text>
        data file to get the
        <el-text class="mx-1" type="primary" size="large">user experience rating report.</el-text>
      </el-text>
    </div>
    <div class="container">
      <el-upload
        class="upload-demo"
        ref="upload"
        drag
        accept=".json"
        :show-file-list="true"
        :before-upload="beforeUpload"
        :on-change="handleChange"
        :on-error="handleUploadError"
        :on-success="handleUploadSuccess"
        :on-progress="handleUploadProgress"
        :http-request="httpRequest"
        :auto-upload="false"
        >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          Drop file here or <em>click to upload</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            Only one json file with a size less than 10MB at a time
          </div>
        </template>
      </el-upload>
    </div>
    <el-button 
      class="upload-submit-button"
      slot="trigger" 
      size="large" 
      type="primary" 
      :disabled="isDisabled"
      @click="submitUpload">
      {{ buttonText }}
    </el-button>
    <div v-if="showProgress" class="progress">
      <el-progress
          :percentage="uploadProgress"
          indeterminate=true
          status="uploading">
        </el-progress>
    </div>
  </div>
</template>

<script>
import { UploadFilled } from '@element-plus/icons-vue'
import { ElButton } from 'element-plus';
import { ref } from 'vue';
import api from "@/api/request.js"
import router from '@/router';
import Footer from '@/components/Footer.vue';
const { v4:uuid4 } = require('uuid')

export default {
  name: 'mainVue',
  components: { UploadFilled, ElButton, Footer },
  data() {
    return {
      fileList: [],
      uploadRef: ref(null),
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      msg: "Upload Your JSON File for A Quick Start",
      showProgress: false,
      uploadProgress: 0,
      isDisabled: false,
      buttonText: 'Upload and Analyse',
      sessionId: uuid4().replace(/-/g, '')
    };
  },
  methods: {
    handleUploadSuccess(response) {
      this.$message.success({
        duration: 5000, 
        message: "Submitted. Waiting for upload"
      })
      this.isDisabled = true
      this.buttonText = 'Redirecting'
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hours = String(currentDate.getHours()).padStart(2, '0');
      const minutes = String(currentDate.getMinutes()).padStart(2, '0');
      const seconds = String(currentDate.getSeconds()).padStart(2, '0');

      const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

      setTimeout(() => {
        router.push({
          path: 'process',
          // path: 'report',
          query: { 
            sessionId: this.sessionId,
            submissionTime: formattedTime
          }
        })
      }, 3000)
    },
    handleUploadError(error) {
      var code = error.code
      var message = error.message

      this.$message.error({
        duration: 4000, 
        message: code + " " + message
      })
    },
    handleUploadProgress(event) {
      this.showProgress = true;
      this.uploadProgress = event.percent || 0;
    },
    handleChange(file, fileList) {
      if(fileList.length > 1) {
        fileList.splice(0, 1)
      }
      this.fileList = fileList
    },
    beforeUpload(file) {
      var appendix = file.name.substring(file.name.lastIndexOf('.') + 1)
      const suffix = appendix === 'json'
      if (!suffix) {
        this.$message.warning({
          duration: 5000, 
          message: appendix.toUpperCase() + " file type is not supported! Please upload JSON file."
        })
        return false
      }
      return true
    },
    submitUpload() {
      if (this.fileList.length === 0) {
        this.$message.warning({
          duration: 5000, 
          message: "Please upload your User Behaviour file first!"
        })
      }
      this.$refs.upload.submit();
    },
    httpRequest(param) {
      let file = param.file
      let fd = new FormData()
      fd.append('file', file)

      return api({
        url: "/upload",
        method: "post",
        params: {
          sessionId: this.sessionId
        },
        data: fd,
        headers: this.headers,
        onUploadProgress: (progressEvent) => {
          this.uploadProgress = Math.round(
            (progressEvent.loaded * 100) / progressEvent.total
          );
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.upload-demo {
  width: 50%;
}
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  margin-top: 10px;
}
.upload-submit-button {
  margin-top: 30px;
  size: 10px;
}
.progress {
  width: 50%;
  margin: 0 auto;
  margin-top: 20px;
}
.text-wrap {
  margin-bottom: 20px;
}
.text-line {
  margin-top: 20px;
}
</style>
