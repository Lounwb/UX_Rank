<template>
    <div class="top-container">
        <div class="top-content">
            <div class="top-item"><img id="logo" src="https://cdn.vuetifyjs.com/docs/images/logos/vuetify-logo-v3-slim-light.svg" alt="Logo"></div>
            <div class="top-item"><el-text class="mx-1" size="small">{{ url }}</el-text></div>
        </div>       
    </div>
    <div class="container-2">
        <div style="flex: 0 0 auto"> <el-text class="mx-1">Rating</el-text></div>
        <div style="margin-left: 648px;"><el-text class="mx-1">Expand view</el-text></div>
    </div>
    <div style="width: 776px; height: 288px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center;">
        <div style="float: left; width: 363.5px; height: 230px;">
            <div style="width: 363.5px; height: 130px; display: flex; justify-content: center; align-items:  center;">
                <el-progress type="circle" :percentage="percentage" :color="changeProgressColor(percentage)">
                    <el-text class="mx-1" size="large" tag="b">{{ percentage }}</el-text>
                </el-progress>
            </div> <!-- 上部分 -->
            <div style="width: 363.5px; height: 20px;">
                <el-text class="mx-1" size="large" style="margin: 0 auto;" tag="b">
                    User experience rating
                </el-text>
            </div> <!-- 中间部分 -->
            <div style="width: 363.5px; height: 40px;">
                <el-text class="mx-1" size="small" style="margin: 0 auto;" type="info">
                    The score may change with different weight coefficients. Please refer to the documentation for specific parameters.
                </el-text>
            </div> <!-- 中间部分 -->
            <div style="width: 363.5px; height: 40px; display: flex; justify-content: center; align-items:  center;">
                <div style="width: 72.7px; height: 20px; display: flex;"></div>
                <div style="width: 72.7px; height: 20px; display: flex;">
                    <div style="width: 30px; margin-top: 3px;">
                        <el-icon color="red"><CaretTop /></el-icon>
                    </div>
                    <div style="width: 42.7px;">
                        <el-text class="mx-1" size="small" style="margin: 0 auto;">0-49</el-text>
                    </div>
                </div>
                <div style="width: 72.7px; height: 20px; display: flex; margin-left: 20px;">
                    <div style="width: 30px; margin-top: 5px; margin-left: 2px;">
                        <i class="icon-square"></i>
                    </div>
                    <div style="width: 42.7px;">
                        <el-text class="mx-1" size="small" style="margin: 0 auto;">50-89</el-text>
                    </div>
                </div>
                <div style="width: 72.7px; height: 20px; display: flex;">
                    <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                        <div class="icon-dot"></div>
                    </div>
                    <div style="width: 42.7px;">
                        <el-text class="mx-1" size="small" style="margin: 0 auto;">90-100</el-text>
                    </div>
                </div>
                <div style="width: 72.7px; height: 20px; display: flex;"></div>
            </div> <!-- 下部分 -->
        </div>
        <div style="width: 1px; height: 50%; background-color: #e0e0e0;"></div>
        <div style="float: left; width: 363.5px; height: 230px; border: 1px solid #000; ">
            <img :src="screenshotUrl" alt="Website Screenshot" />
        </div>
    </div>
    <div class="container-2" style="margin-top: 10px;">
        <div style="flex: 0 0 auto"> <el-text class="mx-1">Problems</el-text></div>
        <div style="margin-left: 630px;"><el-text class="mx-1">Expand view</el-text></div>
    </div>
    <div style="margin-left: -70px; margin-top: 10px;">
        <el-text class="mx-1" style="margin-top: 70px; text-align: left;">It seems that you encountered a total of 
            <el-text type="error" tag="b">{{ problemCount }}</el-text>
            experience issues during this online session. The details are as follows:</el-text>
    </div>
    <div style="width: 776px; margin: 0 auto; margin-top: 10px;">
        <el-collapse v-model="activeNames" @change="handleChange">
            <div>
                <el-collapse-item title="Consistency" name="1">
                    <template #title>
                        <span class="icon-wrapper" style="align-items: center; display: flex;">
                            <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon>
                        <div style="margin-left: 16px;">Unresponsive clicks</div>
                        </span>
                    </template>
                <div style="justify-content: flex-start; display: flex;">
                    When a user clicks on an element (such as a button or link) on a web page, but there is no visible response or action, it can be frustrating and confusing. 
                </div>
            </el-collapse-item>
            </div>
            <el-collapse-item title="Feedback" name="2">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="green"><CircleCheckFilled /></el-icon>
                        <div style="margin-left: 16px;">High bounce rate</div>
                    </span>
                </template>
                <div>
                The bounce rate represents the percentage of visitors who land on a webpage (such as a website homepage) and then leave without interacting with any other pages or elements on the site. 
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="3">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon>
                        <div style="margin-left: 16px; ">Repeat click</div>
                    </span>
                </template>
                <div>
                    Duplicate clicks refer to instances where a user unintentionally or intentionally clicks the same element (such as a button or link) multiple times in quick succession.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="4">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon>
                        <div style="margin-left: 16px;">Page opens slowly</div>
                    </span>
                </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="5">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon>
                        <div style="margin-left: 16px;">Network feedback is slow after clicking</div>
                    </span>
                </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="6">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="green"><CircleCheckFilled /></el-icon>
                        <div style="margin-left: 16px;">Click to report an error</div>
                    </span>
                </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="7">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <!-- <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon> -->
                        <el-icon style="margin-left: 8px;" color="green"><CircleCheckFilled /></el-icon>
                        <div style="margin-left: 16px;">Page loading error</div>
                    </span>
                </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="8">
                <template #title>
                    <span class="icon-wrapper" style="align-items: center; display: flex;">
                        <el-icon style="margin-left: 8px;" color="red"><CircleCloseFilled /></el-icon>
                        <div style="margin-left: 16px;">Page loading white screen</div>
                    </span>
                </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
        </el-collapse>
    </div>
    <div class="container-2">
        <div style="flex: 0 0 auto"> <el-text class="mx-1">Metrics</el-text></div>
        <div style="margin-left: 644px;"><el-text class="mx-1">Expand view</el-text></div>
    </div>
    <div style="width: 776px; margin: 0 auto;">
        <el-collapse v-model="activeNames" @change="handleChange">
            <div>
                <el-collapse-item title="Consistency" name="1">
                    <template #title>
                        <span class="icon-wrapper" style="align-items: center; display: flex;">
                            <div style="width: 30px; margin-top: 3px;">
                                <el-icon color="red"><CaretTop /></el-icon>
                            </div>
                        <div style="margin-left: 5px;">Loading Performance</div>
                        </span>
                    </template>
                <div>
                Consistent with real life: in line with the process and logic of real
                life, and comply with languages and habits that the users are used to;
                </div>
                <div>
                Consistent within interface: all elements should be consistent, such
                as: design style, icons and texts, position of elements, etc.
                </div>
            </el-collapse-item>
            </div>
            <el-collapse-item title="Feedback" name="2">
                <template #title>
                        <span class="icon-wrapper"  >
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div> -->
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div> -->
                            <div style="width: 30px; margin-top: 3px;">
                                <el-icon color="red"><CaretTop /></el-icon>
                            </div>
                        <div style="margin-left: 5px;">Largest Contentful Paint</div>
                        </span>
                    </template>
                <div>
                Operation feedback: enable the users to clearly perceive their
                operations by style updates and interactive effects;
                </div>
                <div>
                Visual feedback: reflect current state by updating or rearranging
                elements of the page.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Efficiency" name="3">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <div style="width: 30px; margin-top: 3px;">
                                <el-icon color="red"><CaretTop /></el-icon>
                            </div>
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div> -->
                        <div style="margin-left: 5px;">Total Page Load Time</div>
                        </span>
                    </template>
                <div>
                Simplify the process: keep operating process simple and intuitive;
                </div>
                <div>
                Definite and clear: enunciate your intentions clearly so that the
                users can quickly understand and make decisions;
                </div>
                <div>
                Easy to identify: the interface should be straightforward, which helps
                the users to identify and frees them from memorizing and recalling.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="4">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div>
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div> -->
                        <div style="margin-left: -2px;">First Interaction Performance</div>
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="5">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <!-- <div style="width: 30px; margin-top: 3px;">
                                <el-icon color="red"><CaretTop /></el-icon>
                            </div> -->
                            <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div>
                            <div style="margin-left: -5px;">First Input Delay</div>
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="6">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div> -->
                            <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div>
                            <div style="margin-left: -2px;">First Interaction Performance</div>    
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="7">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div> -->
                            <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div>
                            <div style="margin-left: -2px;">Console Error Count</div>  
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="8">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div>
                            <div style="margin-left: -5px;">Console Warning Count</div>     
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="9">
                <template #title>
                        <span class="icon-wrapper" style="align-items: center;">
                            <!-- <div style="width: 30px; margin-top: 5px; margin-left: 8px;">
                                <i class="icon-square"></i>
                            </div> -->
                            <div style="width: 30px; margin-top: 5px; margin-left: 9px;">
                                <div class="icon-dot"></div>
                            </div>
                            <div style="margin-left: -5px;">Click Latency</div>
                        </span>
                    </template>
                <div>
                Decision making: giving advices about operations is acceptable, but do
                not make decisions for the users;
                </div>
                <div>
                Controlled consequences: users should be granted the freedom to
                operate, including canceling, aborting or terminating current
                operation.
                </div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>

<script>
import  html2canvas from "../../public/js/html2canvas.min.js"
import VueWebScreenShot from 'vue-web-screen-shot'
import api from "@/api/request.js"

export default {
    components: { VueWebScreenShot },
    data() {
        return {
            percentage: 47,
            url: 'https://www.xiaohongshu.com/explore',
            problemCount: 5,
            screenshotUrl: '',
        };
    },
    mounted() {
        window.addEventListener('scroll', this.handleScroll);
        this.captureScreenshot(this.url);
    },
    beforeUnmount() {
        window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        handleScroll() {
            var topContainer = document.querySelector('.top-container');
            var scrollTop = window.pageYOffset || document.documentElement.scrollTop;

            if (scrollTop > 0) {
                topContainer.style.top = '0';
            } else {
                topContainer.style.top = '';
            }
        },
        captureScreenshot(siteUrl) {
            api({
                url: "https://s0.wp.com/mshots/v1/" + siteUrl +"?w=363.5&h=230",
                method: "get",
                responseType: 'arraybuffer'
            }).then(response => {
                const imageData = response.data;
                const base64Image = btoa(
                new Uint8Array(imageData).reduce(
                    (data, byte) => data + String.fromCharCode(byte),
                    ''
                )
                );
                const imageUrl = `data:image/jpeg;base64,${base64Image}`;

                this.screenshotUrl = imageUrl;
            })
        },
        changeProgressColor(progress) {
            if (progress < 50) {
                return 'rgb(248, 6, 12)'
            }
            if (progress < 90) {
                return 'rgb(244, 172, 62)'
            }
            return 'rgb(52, 206, 106)'
        }
    }
};
</script>

<style>
#logo {
  width: 18px;  /* 设置宽度为50像素 */
  height: 18px; /* 设置高度为50像素 */
}
.top-container {    
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 5px;
  background-color: rgb(255, 255, 255);
  border-bottom: 1px solid rgb(75, 72, 72);  /* 添加底部黑线 */
  transition: top 0.3s;  /* 添加过渡效果，使切换时更平滑 */
}
.top-content {
  display: flex;  /* 使用 Flexbox 布局 */
  align-items: center;  /* 垂直居中对齐内部元素 */
}
.top-item {
  margin-right: 10px;
}
.box-container {
    display: flex;
  justify-content: center; /* 水平居中对齐 */
  align-items: center; /* 垂直居中对齐 */
  margin-top: -26px;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(2, 376px);
  grid-gap: 14px;
  width: 776px;
  height: 360px;
  margin: 0 auto;
  align-items: center; /* 垂直居中对齐 */
}
.container-2 {
    width: 776px; 
    height: 36px; 
    display: flex; 
    margin: 0 auto; 
    align-items: center; 
    border-bottom: 1px solid rgb(75, 72, 72);  /* 添加底部黑线 */
}
.grid-item {
  width: 100%;
  height: 72px;
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: flex-start; /* 左对齐 */
}
.icon-square {
    width: 10px; /* 调整宽度和高度以达到所需的图标大小 */
  height: 10px;
  background-color: rgb(249, 171, 57); /* 调整背景颜色 */
  display: flex;
  justify-content: center;
  align-items: center;
}
.icon-dot {
    width: 10px; /* 调整宽度和高度以达到所需的圆形大小 */
  height: 10px;
  background-color: rgb(36, 205, 104); /* 调整背景颜色 */
  border-radius: 50%; /* 将边框半径设置为50%以形成圆形 */
}
.icon-wrapper {
    align-items: center; 
    display: flex;  
}
</style>