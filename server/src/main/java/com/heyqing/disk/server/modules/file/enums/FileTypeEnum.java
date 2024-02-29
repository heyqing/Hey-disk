package com.heyqing.disk.server.modules.file.enums;

import com.heyqing.disk.core.exception.HeyDiskBusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * ClassName:FileTypeEnum
 * Package:com.heyqing.disk.server.modules.file.enums
 * Description:
 * 文件类型枚举类
 *
 * @Date:2024/2/28
 * @Author:Heyqing
 */
@AllArgsConstructor
@Getter
public enum FileTypeEnum {

    NORMAL_FILE(1,"NORMAL_FILE",1,fileSuffix ->true),
    ARCHIVE_FILE(2,"ARCHIVE_FILE",2,fileSuffix -> {
        List<String> matchFileSuffixes = Arrays.asList(".rar",".zip",".cab",".iso",".jar",".ace",".7z",".tar",".gz","arj","lah","uue",".bz",".xz", ".zst", ".lz4", ".lzma", ".lzh");
        return StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    EXCEL_FILE(3,"EXCEL_FILE",3,fileSuffix -> {
        List<String> matchFileSuffixes = Arrays.asList(".xlsx",".xls");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    WORD_FILE(4,"WORD_FILE",4,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".docx",".doc");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    PDF_FILE(5,"PDF_FILE",5,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".pdf");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    TXT_FILE(6,"TXT_FILE",6,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".txt");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    IMAGE_FILE(7,"IMAGE_FILE",7,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".bmp",".gif",".png",".ico",".eps",".psd",".tga",".tiff",".jpg",".jpeg");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    AUDIO_FILE(8,"AUDIO_FILE",8,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".mp3",".mkv",".mpg",".rm",".wma");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    VIDEO_FILE(9,"VIDEO_FILE",9,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".mp4",".avi",".3gp",".flv",".rmvb",".mov");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    POWER_FILE(10,"POWER_FILE",10,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".ppt",".pptx");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    SOURCE_CODE_FILE(11,"SOURCE_CODE_FILE",11,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".java",".obj",".c",".h",".html",".net",".php",".css",".js",".ftl",".jsp",".asp");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    }),
    CSV_FILE(12,"CSV_FILE",12,fileSuffix ->{
        List<String> matchFileSuffixes = Arrays.asList(".csv");
        return  StringUtils.isNotBlank(fileSuffix) && matchFileSuffixes.contains(fileSuffix);
    });


    /**
     * 文件类型code
     */
    private Integer code;
    /**
     * 文件类型名称
     */
    private String desc;
    /**
     * 文件类型排序-降序
     */
    private Integer order;
    /**
     * 文件类型匹配器
     */
    private Predicate<String> tester;

    /**
     * 根据文件名称后缀来获取对应的文件类型code
     *
     * @param fileSuffix
     * @return
     */
    public static Integer getFileTypeCode(String fileSuffix) {
        Optional<FileTypeEnum> result = Arrays.stream(values())
                .sorted(Comparator.comparing(FileTypeEnum::getOrder).reversed())
                .filter(value -> value.getTester().test(fileSuffix))
                .findFirst();
        if (result.isPresent()) {
            return result.get().getCode();
        }
        throw new HeyDiskBusinessException("获取文件类型失败");
    }
}
