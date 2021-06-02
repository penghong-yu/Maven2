const path = require('path');
const webpack = require('webpack');
const proxyObj = {}

module.exports = {
    lintOnSave: false,
    outputDir: 'bundle',
    devServer: {
        public: '192.168.43.46:8080',
        hot: true,
        disableHostCheck: true,
        proxy: {
            '/api': {
                // 此处的写法，目的是为了 将 /api 替换成 https://www.baidu.com/
                target: 'http://localhost:9000',
                // 允许跨域
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },
    configureWebpack: {
        plugins: [
            new webpack.BannerPlugin({
                banner: ""
            }),
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "windows.jQuery": "jquery"
            })
        ]
    }
}


