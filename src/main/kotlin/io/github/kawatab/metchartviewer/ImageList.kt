/**
 * metchartViewer/ImageList.kt - class for handling image list
 *
 * Copyright (c) 2019 Yasuhiro Yamakawa <kawatab@yahoo.co.jp>
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package io.github.kawatab.metchartviewer

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.regex.Matcher
import java.util.regex.Pattern
import javafx.scene.image.Image


class ImageList(sourceURL: String, imageURL: String) {
    private var imageIndex = 0
    private val imageList: List<Image>

    init {
	imageList = getURLList(sourceURL, imageURL).map { Image(it, true) }
    }

    private fun getURLList(sourceURL: String, imageURL: String): List<String> {
	val url = URL(sourceURL)
	val connection = url.openConnection() as HttpURLConnection
	connection.setRequestMethod("GET")
	
	return mutableListOf<String>().apply {
	    if (connection.getResponseCode() == 200) {
		val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
		val pattern = Pattern.compile(imageURL)
		var line = reader.readLine()
		
		while (line != null) {
		    val match = pattern.matcher(line)
		    if (match.find()) add(match.group())
		    line = reader.readLine()
		}
	    }
	}
    }

    fun getCurrentImage(): Image = imageList[imageIndex]
    fun setPreviousImage(): Boolean = hasPreviousImage().also {if (it) imageIndex -= 1}
    fun setNextImage(): Boolean = hasNextImage().also {if (it) imageIndex += 1}
    fun hasAnyImages(): Boolean = imageList.size > 0
    fun hasPreviousImage(): Boolean = imageIndex > 0
    fun hasNextImage(): Boolean = imageIndex < imageList.size - 1
}
