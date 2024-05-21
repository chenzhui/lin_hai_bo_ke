const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  /*devServer:{
    proxy:{
      '^/api':{
        target:"http://114.55.38.94:8001",
        changeOrigin:true,
        //pathRewritre:{
        //  '^/a':''
        //}
      }
    }
  }*/

})
