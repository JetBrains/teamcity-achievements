<div id="grantedAchievements"></div>

<script type="text/javascript">
    var updater = new BS.PeriodicalUpdater('grantedAchievements', window['base_uri'] + "/grantedAchievements.html", {
        frequency: 10,
        evalScripts: true
    });

    $('grantedAchievements').refresh = function() {
        BS.ajaxUpdater('grantedAchievements', window['base_uri'] + "/grantedAchievements.html");
    }
</script>
