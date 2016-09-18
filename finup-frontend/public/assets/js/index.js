(function() {

	"use strict";

		// Forms.
		(function() {

			// Vars.
				var $form = document.querySelectorAll('form')[0],
					$submit = document.querySelectorAll('form input[type="submit"]')[0],
					$message;

			// Bail if addEventListener isn't supported.
				if (!('addEventListener' in $form))
					return;

			// Message.
				$message = document.createElement('span');
					$message.classList.add('message');
					$form.appendChild($message);

				$message._show = function(type, text) {

					$message.innerHTML = text;
					$message.classList.add(type);
					$message.classList.add('visible');

					window.setTimeout(function() {
						$message._hide();
					}, 3000);

				};

				$message._hide = function() {
					$message.classList.remove('visible');
				};

			// Events.
				$form.addEventListener('submit', function(event) {

					event.stopPropagation();
					event.preventDefault();

					// Hide message.
						$message._hide();

					// Disable submit.
						$submit.disabled = true;

					// Process
					$.post( "/v1/signups", $("#signup-form").serialize(), function() {
					  	$form.reset();
					    $submit.disabled = false;
					    $message._show('success', 'Thank you!');
					}).fail(function() {
					    $message._show('failure', 'Something went wrong. Please try again.');
					});

				});

		})();

})();