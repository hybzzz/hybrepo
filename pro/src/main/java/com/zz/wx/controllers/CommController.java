package com.zz.wx.controllers;

import com.zz.wx.service.CoreService;
import com.zz.wx.util.CheckUtil;
import com.zz.wx.util.ConfigUtil;
import com.zz.wx.util.PayCommonUtil;
import com.zz.wx.util.WxPayUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@RequestMapping("/pro/comm")
public class CommController {
    @RequestMapping(value = "wx",method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // 校验通过，原样返回echostr参数内容
        }
    }
    @Autowired
    private CoreService coreService;
    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public void indexhandler(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(coreService.processRequest(request));
        out.close();
    }
    @RequestMapping("queryK")
    public String queryK(){
        return "queryK";
    }
    @RequestMapping("/upload1")
    private void importPic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = req.getRealPath("/");
        factory.setRepository(new File(path));
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        try {
            List<FileItem> list = (List<FileItem>) upload.parseRequest(req);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String value = new String(item.getString().getBytes("iso8859-1"), "utf-8");
                    System.out.println(value);
                } else {
                    InputStream is = item.getInputStream();
                    int index;
                    byte[] bytes = new byte[1024];
                    FileOutputStream downloadFile = new FileOutputStream(path+name);
                    while ((index = is.read(bytes)) != -1) {
                        downloadFile.write(bytes, 0, index);
                        downloadFile.flush();
                    }
                    downloadFile.close();
                    is.close();


//                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
//                    while ((line = br.readLine()) != null) {
//                        datas.add(line);
//                    }
//                    System.out.println(json.toString());
//                    resp.setContentType("application/json; charset=utf-8");
//                    resp.getWriter().write(json.toString());;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @RequestMapping("/upload")
    public void uploadPic(MultipartFile pic, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {

        try {
            // 获取图片原始文件名
            String originalFilename = pic.getOriginalFilename();
            System.out.println(originalFilename);

            // 文件名使用当前时间
            String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // 获取上传图片的扩展名(jpg/png/...)
            String extension = FilenameUtils.getExtension(originalFilename);

            // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
            // 图片上传的绝对路径
//            String classpath = ClassLoader.getSystemResource("").getPath();
            String classpath = "/usr/local/www/imgs/";
            String dirName= classpath+ "upload/" ;
            String path = classpath+"upload/" + name + "." + extension;
            String imgPath="/imgs/upload/" + name + "." + extension;
            File dir = new File(dirName);
            if(dir.exists()||dir.mkdirs()) {//如果不存在 创建文件夹
                System.out.println("文件夹创建成功");
                // 上传图片
                pic.transferTo(new File(path));
                System.out.println("图片上传成功");
                // 将相对路径写回（json格式）
                JSONObject jsonObject = new JSONObject();
                // 将图片上传到本地
                jsonObject.put("path", imgPath);

                // 设置响应数据的类型json
                response.setContentType("application/json; charset=utf-8");
                // 写回
                response.getWriter().write(jsonObject.toString());

            }



        } catch (Exception e) {
            throw new RuntimeException("服务器繁忙，上传图片失败");
        }
    }



//    @ApiOperation(value = "微信支付调用", httpMethod = "POST")
    @RequestMapping("/couponsConfirm")
    public String couponsConfirm(Model m, @RequestParam("openid")String openid, @RequestParam("orderNo")String orderNo) {
//openid可通过微信高级接口oath2.0网页授权接口获取到用户信息,此接口本文中就不提供了，如果有需要，请留言。
        m.addAttribute("openid", openid);
//orderNo是你的商品订单号，自行生成的随机订单号，但是要保证随机性，不能有重复订单号。
        m.addAttribute("orderNo", orderNo);
        String timeStamp= PayCommonUtil.create_timestamp();
        String nonceStr=PayCommonUtil.create_nonce_str();
        m.addAttribute("appid", ConfigUtil.APPID);
        m.addAttribute("timestamp", timeStamp);
        m.addAttribute("nonceStr", nonceStr);
        m.addAttribute("openid",openid);

        String prepayId= WxPayUtil.unifiedorder("外卖下单",orderNo, openid);
        SortedMap<Object,Object> signParams = new TreeMap<Object,Object>();
        signParams.put("appId", ConfigUtil.APPID);
        signParams.put("nonceStr",nonceStr);
        signParams.put("package", "prepay_id="+prepayId);
        signParams.put("timeStamp", timeStamp);
        signParams.put("signType", "MD5");

        // 生成支付签名，要采用URLENCODER的原始值进行SHA1算法！  
        String sign= PayCommonUtil.createSign(signParams);


        m.addAttribute("paySign", sign);

        m.addAttribute("packageValue", "prepay_id="+prepayId);

        return "跳转到你的支付页面";
    }
}
