/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */
 //BS_KEYWORD='UPLOADFILE';
 //RECORD_ID=date.getMilliseconds;
   
$(function () {
    'use strict';
   
    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},    	
        url: '/system/metaData?cmd=upload&BS_KEYWORD='+BS_KEYWORD+'&RECORD_ID='+RECORD_ID
    });

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );
    if (window.location.hostname === '127.0.0.1') {
        // Demo settings:
    	//alert('bbbb'+window.location.hostname);
    	$('#fileupload').addClass('fileupload-processing');
        $('#fileupload').fileupload('option', {
            url: '/system/metaData?cmd=upload&BS_KEYWORD='+BS_KEYWORD+'&RECORD_ID='+RECORD_ID,
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 1024000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png|xlsx|doc|zip|xls|xlsx)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '/system/metaData?cmd=uploadCor',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
        
        $(this).removeClass('fileupload-processing');
    } else {
        // Load existing files:
        $('#fileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#fileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#fileupload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
    }

});
