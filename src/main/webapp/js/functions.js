/**
 * WaitScreen
 *
 * Define functions for handling the wait screen.
 */
const WaitScreen = function () {
    return {
        show: function () {
            $("div.wait-overlay").addClass("show-overlay");
        },
        hide: function () {
            $("div.wait-overlay").removeClass("show-overlay");
        }
    }
}();

/**
 * AlertScreen
 *
 * Define functions for handling the alert screen.
 */
const AlertScreen = function () {
    return {
        setText: function (text) {
            $("#alert-msg").html(text);
        },
        show: function () {
            $("div.alert-overlay").addClass("show-overlay");
        },
        hide: function () {
            $("div.alert-overlay").removeClass("show-overlay");
        },
        reload: function () {
            location.reload();
        }
    }
}();

/**
 * ExecuteAction
 *
 * Define functions for running AJAX requests.
 */
const ExecuteAction = function () {
    return {
        execute: function (url) {
            WaitScreen.show();
            location.hash = "#dataTable";
            $.ajax({
                method: "POST",
                url: url
            }).done(function (msg) {
                console.log("ExecuteAction response: " + msg);
                WaitScreen.hide();
                location.reload();
            }).fail(function (jqXHR, textStatus) {
                console.log("ExecuteAction failed: " + textStatus);
                console.log("ExecuteAction response: " + jqXHR.responseText);
                WaitScreen.hide();
                let oResponse = JSON.parse(jqXHR.responseText);
                AlertScreen.setText(oResponse.message);
                AlertScreen.show();
            });
        }
    }
}();
