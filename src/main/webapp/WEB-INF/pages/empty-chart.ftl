<#include "view-head.ftl"/>
<div class="col-md-3">
    <div class="panel panel-default">
        <div class="panel-heading">重点推荐位请求、展示、点击指标</div>
        <div class="panel-body">
            <form action="/monitor">
                <label>重点推荐位：</label>
                <select name="placement" class="fix-width">
                    <#list rec_placement as rp>
                        <option value="${rp}">${rp}</option>
                    </#list>
                </select>
                <input type="hidden" name="ext" value="mercury"/>
                <button type="submit">查询</button>
            </form>
        </div>
    </div>
</div>
<#include "foot.ftl"/>
