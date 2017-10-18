package com.myproject.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	// いらないかもID
	Integer id;
	// 一般かどうかフラグ
	boolean rFlag;
	// タイトル
	String title;
	// 表紙のファイル名
	String fileName;
	// 元作品
	String original;
	// 評価
	Integer star;
}
