/**
 * metchartViewer/MetChartViewer.kt - Main application class
 *
 * Copyright (c) 2019 Yasuhiro Yamakawa <kawatab@yahoo.co.jp>
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package io.github.kawatab.metchartviewer

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.image.Image
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyCode
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class MetChartViewer : Application () {
    private val sourceURL = "https://www.metoffice.gov.uk/weather/maps-and-charts/surface-pressure"
    private val imageURL = "https://www.metoffice.gov.uk/public/data/CoreProductCache/SurfacePressureChart/Item/ProductId/[0-9]*"
    private val imageList = ImageList(sourceURL, imageURL)

    override fun start(stage: Stage) {
	if (imageList.hasAnyImages()) {
	    stage.title = "Met Chart Viewer"
	    val fxmlLoader = FXMLLoader(javaClass.getResource("/mainwindow.fxml")) 
	    val controller = WindowController(imageList, sourceURL)
	    fxmlLoader.setController(controller)
	    val parent = fxmlLoader.load<Parent>() 
	    val scene = Scene(parent)
	    stage.scene = scene
	    stage.show()

	    scene.setOnKeyPressed {
		when (it.getCode()) {
		    KeyCode.LEFT, KeyCode.KP_LEFT, KeyCode.PAGE_UP, KeyCode.P -> controller.moveToPrevious()
		    KeyCode.RIGHT, KeyCode.KP_RIGHT, KeyCode.PAGE_DOWN, KeyCode.N -> controller.moveToNext()
		    else -> {}
		}
	    }
	} else {
	    Platform.exit()
	}
    }
}
