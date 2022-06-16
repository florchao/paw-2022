//package ar.edu.itba.paw.service.mails;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class ApplyMail {
//
//
//    private String subjectEs;
//
//    private String contentEs;
//
//    private String subjectEn;
//
//    private String contentEn;
//
//    public ApplyMail() {
//    }
//
//    public ApplyMail(String name, String jobTitle, long jobid) {
//        System.out.println("AAAAAA");
//        this.subjectEs =  "¡"+ name + " aplicó para "+jobTitle+"!";
//        System.out.println("BBBB");
//        System.out.println("MESSAGE "+messageSource);
//        System.out.println("DDDDD");
//        this.contentEs = String.format(content);
//        System.out.println("CCCC");
//        this.subjectEn = name + " applied for "+jobTitle+"!";
//        System.out.println("EEEE");
//        this.contentEn = String.format(content);
//        System.out.println("ACA LLEGUE");
//        System.out.println(subjectEn);
//        System.out.println(subjectEs);
//        System.out.println(contentEn);
//        System.out.println(contentEs);
//    }
//
//    public String getSubjectEs() {
//        return subjectEs;
//    }
//
//    public String getContentEs() {
//        return contentEs;
//    }
//
//    public String getSubjectEn() {
//        return subjectEn;
//    }
//
//    public String getContentEn() {
//        return contentEn;
//    }
//}
