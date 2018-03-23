/**
 * Created by Administrator on 17-3-21.
 */
/**
 *��ʼ����ǩҳ�����Ҽ��˵�����
 */


function init_right_click_menu(){
    $('#right_click_menu').menu({
        onClick : function(item) {
            //��ǰ��ǩ����
            var curTabTitle = $(this).data('tabTitle');
            //����Ĳ˵���
            var type = $(item.target).attr('type');
            //ˢ��
            if (type === 'refresh') {
                layout_center_refreshTab(curTabTitle);
                return;
            }
            //�ر�
            if (type === 'close') {
                var t = $('#layout_center_tabs').tabs('getTab', curTabTitle);
                if (t.panel('options').closable) {
                    $('#layout_center_tabs').tabs('close', curTabTitle);
                }
                return;
            }

            var allTabs = $('#layout_center_tabs').tabs('tabs');
            var closeTabsTitle = [];

            $.each(allTabs, function() {
                var opt = $(this).panel('options');
                if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {//�ر�������
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type === 'closeAll') {//�ر�ȫ��
                    closeTabsTitle.push(opt.title);
                }
            });
            //�������еı�ǩ��һ�ر�
            for ( var i = 0; i < closeTabsTitle.length; i++) {
                $('#layout_center_tabs').tabs('close', closeTabsTitle[i]);
            }
        }
    });
}

/**
 * ��ʼ����ǩҳ������
 */
function initTabTools(){
    $('#layout_center_tabs').tabs({
        fit:true,
        border:false,
        onContextMenu:function(e,title){
            e.preventDefault();
            $('#right_click_menu').menu('show',{
                left:e.pageX,
                top:e.pageY
            }
            ).data('tabTitle',title);
        }
    });
    $('#right_click_menu').menu({
        onClick : function(item) {
//            alert(item.text);
            var curTabTitle = $(this).data('tabTitle');
            var type = item.name;
//           alert(type);
            if (type == 'refresh') {
                layout_center_refreshTab(curTabTitle);
//                alert("111");
                return;
            }
            //�ر�
            if (type == 'close') {
                var t = $('#layout_center_tabs').tabs('getTab', curTabTitle);
                if (t.panel('options').closable) {
                    $('#layout_center_tabs').tabs('close', curTabTitle);
                }
                return;
            }
            var allTabs = $('#layout_center_tabs').tabs('tabs');
            var closeTabsTitle = [];

            $.each(allTabs, function() {
                var opt = $(this).panel('options');
                if (opt.closable && opt.title != curTabTitle && type == 'closeOther') {
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type == 'closeAll') {//�ر�ȫ��
                    closeTabsTitle.push(opt.title);
                }
            });
            for ( var i = 0; i < closeTabsTitle.length; i++) {
                $('#layout_center_tabs').tabs('close', closeTabsTitle[i]);
            }
        }
    });

}


