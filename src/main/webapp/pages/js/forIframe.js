function setIframeHeight(iframe) {
			if (iframe) {
				var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
				if (iframeWin.document.body) {
					var height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
					alert(height);
					iframe.height = height;
				}
			}
		};