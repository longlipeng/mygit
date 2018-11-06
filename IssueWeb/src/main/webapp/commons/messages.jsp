<s:if test="hasActionErrors()">
	<div class="error">
		<s:actionerror />
	</div>
</s:if>

<s:if test="hasActionMessages()">
	<div class="message" id="message">
		<s:actionmessage />
	</div>
	<script type="text/javascript">
		highlight("message");
		window.setTimeout(function(){document.getElementById("message").style.display="none";}, 3000);
	</script>
</s:if>

