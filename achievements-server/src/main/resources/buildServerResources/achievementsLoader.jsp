<div id="grantedAchievements"></div>

<script type="text/javascript">
    var updater = new BS.PeriodicalUpdater('grantedAchievements', window['base_uri'] + "/grantedAchievements.html", {
        frequency: 10,
        evalScripts: true
    });

    $('grantedAchievements').refresh = function() {
        updater.onTimerEvent();
    }
</script>
