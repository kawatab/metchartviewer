/**
 * metchartViewer/WindowController.kt - Window controller class
 *
 * Copyright (c) 2019 Yasuhiro Yamakawa <kawatab@yahoo.co.jp>
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package io.github.kawatab.metchartviewer

import java.net.URL
import java.util.ResourceBundle
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView


class WindowController(imageList: ImageList, sourceURL: String) : Initializable {
    @FXML lateinit private var imageView: ImageView
    @FXML lateinit private var previousButton: Button
    @FXML lateinit private var nextButton: Button
    @FXML lateinit private var sourceLabel: Label

    private val imageList = imageList
    private val sourceURL = sourceURL
    
    override fun initialize(location: URL?, resources: ResourceBundle?) {
	sourceLabel.text = "source: $sourceURL"
	setImageAndButtonStatus()
    }

    fun clickPrevious(@Suppress("UNUSED_PARAMETER") e: ActionEvent) {
	moveToPrevious()
    }
    
    fun clickNext(@Suppress("UNUSED_PARAMETER") e: ActionEvent) {
	moveToNext()
    }
    
    fun moveToPrevious() {
	if (imageList.setPreviousImage()) setImageAndButtonStatus()
    }

    fun moveToNext() {
	if (imageList.setNextImage()) setImageAndButtonStatus()
    }

    private fun setImageAndButtonStatus() {
	imageView.setImage(imageList.getCurrentImage())
	previousButton.setDisable(!imageList.hasPreviousImage())
	nextButton.setDisable(!imageList.hasNextImage())
    }
}
