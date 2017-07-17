/*******************************************************************************
 * Copyright 2013-2017 alladin-IT GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package at.alladin.rmbt.android.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import at.alladin.openrmbt.android.BuildConfig;
import at.alladin.rmbt.android.main.RMBTMainActivity;
import at.alladin.rmbt.android.util.CheckTestResultDetailTask;
import at.alladin.rmbt.android.util.ConfigHelper;
import at.alladin.rmbt.android.util.EndTaskListener;
import at.alladin.rmbt.android.views.ResultDetailsView.ResultDetailType;
import at.alladin.rmbt.client.v2.task.result.QoSServerResult.DetailType;
import at.alladin.rmbt.client.v2.task.result.QoSServerResultCollection;
import at.alladin.rmbt.client.v2.task.result.QoSTestResultEnum;

import at.alladin.openrmbt.android.R;

/**
 * 
 * @author lb
 *
 */
public class ResultQoSDetailBoxView extends ScrollView implements EndTaskListener<JSONArray>, OnItemClickListener, OnClickListener {

	public final static Map<QoSTestResultEnum, Integer> ICON_FONT_MAP;

	static {
		ICON_FONT_MAP = new HashMap<>();
		ICON_FONT_MAP.put(QoSTestResultEnum.WEBSITE, R.string.ifont_web_rendering);
		ICON_FONT_MAP.put(QoSTestResultEnum.VOIP, R.string.ifont_voip);
		ICON_FONT_MAP.put(QoSTestResultEnum.TRACEROUTE, R.string.ifont_traceroute);
		ICON_FONT_MAP.put(QoSTestResultEnum.UDP, R.string.ifont_udp);
		ICON_FONT_MAP.put(QoSTestResultEnum.TCP, R.string.ifont_tcp);
		ICON_FONT_MAP.put(QoSTestResultEnum.HTTP_PROXY, R.string.ifont_http_proxy);
		ICON_FONT_MAP.put(QoSTestResultEnum.NON_TRANSPARENT_PROXY, R.string.ifont_non_transparent);
		ICON_FONT_MAP.put(QoSTestResultEnum.DNS, R.string.ifont_dns);
	}

	private final RMBTMainActivity activity;
	
    private final String uid;
	
	private View view;
	
	private EndTaskListener<JSONArray> resultFetchEndTaskListener;
	
    private CheckTestResultDetailTask testResultDetailTask;;
    
    private JSONArray testResult;
    
    private QoSServerResultCollection results;

    /**
     * 
     * @param context
     * @param activity
     * @param uid
     * @param jsonArray
     */
	public ResultQoSDetailBoxView(Context context, RMBTMainActivity activity, String uid, JSONArray jsonArray) {
		this(context, null, activity, uid, jsonArray);
	}
	
	/**
	 * 
	 * @param context
	 * @param activity2
	 */
	public ResultQoSDetailBoxView(Context context, AttributeSet attrs, RMBTMainActivity activity, String uid, JSONArray jsonArray) {
		super(context, attrs);
		setFillViewport(true);
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		this.uid = uid;
		this.view = createView(layoutInflater);
		this.testResult = jsonArray;
	}
    
    public void initialize (EndTaskListener<JSONArray>  resultFetchEndTaskListener) {
    	this.resultFetchEndTaskListener = resultFetchEndTaskListener;
    	
        if ((testResultDetailTask == null || testResultDetailTask != null || testResultDetailTask.isCancelled()) && uid != null)
        {
        	if (this.testResult!=null) {
        		taskEnded(this.testResult);
        	}
        	else {
            	System.out.println("initializing ResultDetailsView");
            	
                testResultDetailTask = new CheckTestResultDetailTask(activity, ResultDetailType.QUALITY_OF_SERVICE_TEST);
                
                testResultDetailTask.setEndTaskListener(this);
                testResultDetailTask.execute(uid);        		
        	}
        }
    }
    
    /**
     * 
     * @param inflater
     * @return
     */
	public View createView(final LayoutInflater inflater)
    {        
    	final View view = inflater.inflate(R.layout.result_qos_details_box, this);
        
        return view;
    }
	
	public View getView() {
		return view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see at.alladin.rmbt.android.util.EndTaskListener#taskEnded(org.json.JSONArray)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void taskEnded(JSONArray result) {
		System.out.println("ResultQoSDetail taskEnded");
		this.testResult = result;
		
		if (resultFetchEndTaskListener != null) {
			resultFetchEndTaskListener.taskEnded(result);
		}
		
		ProgressBar resultProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		TextView resultTextView = (TextView) view.findViewById(R.id.info_text);
		
		try {
			results = new QoSServerResultCollection(result);
			
			View leftList = view.findViewById(R.id.qos_category_list_left);
			View rightList = view.findViewById(R.id.qos_category_list_right);

			ViewGroup[] lists = new ViewGroup[] {(ViewGroup)leftList, (ViewGroup)rightList};

			List<HashMap<String, String>> itemList = new ArrayList<HashMap<String,String>>();
			int index = 0;
			for (QoSTestResultEnum type : QoSTestResultEnum.values()) {
				if (results.getQoSStatistics().getTestCounter(type) > 0) {
					HashMap<String, String> listItem = new HashMap<String, String>();
					listItem.put("name", ConfigHelper.getCachedQoSNameByTestType(type, activity));
					listItem.put("icon", getResources().getString(ICON_FONT_MAP.get(type)));
					listItem.put("type_name", type.toString());
					listItem.put("index", String.valueOf(index++));
					itemList.add(listItem);
				}
			}
			
            ListAdapter valueList = new SimpleAdapter(activity, itemList, R.layout.qos_category_box_item, new String[] {
            "icon", "name"}, new int[] { R.id.icon, R.id.name});

    		resultProgressBar.setVisibility(View.GONE);
    		
            if (valueList.getCount() > 0) {
            	
				for (int i = 0; i < valueList.getCount(); i++) {
					View v = valueList.getView(i, null, null);
					ViewGroup vgList = lists[i % 2];

					QoSTestResultEnum key = QoSTestResultEnum.valueOf(((HashMap<String, String>)valueList.getItem(i)).get("type_name"));
					if (results.getQoSStatistics().getFailureCounter(key) > 0) {
						ImageView img = (ImageView) v.findViewById(R.id.status);
						if (img != null) {
							img.setImageResource(R.drawable.traffic_lights_red);
						}
					}

					TextView status = (TextView) v.findViewById(R.id.qos_type_status);
					status.setText((results.getQoSStatistics().getTestCounter(key) - results.getQoSStatistics().getFailedTestsCounter(key))
							+ "/" + results.getQoSStatistics().getTestCounter(key));

					v.setOnClickListener(this);
					v.setTag(valueList.getItem(i));
					vgList.addView(v);
				}

				for (int i = 0; i < lists.length; i++) {
					lists[i].invalidate();
					lists[i].setVisibility(View.VISIBLE);
				}

        		resultTextView.setVisibility(View.GONE);
            }
            else {
            	resultTextView.setText(R.string.result_qos_error_no_data_available);
            }

		} catch (Throwable t) {
			resultTextView.setText(R.string.result_qos_error_no_data_available);
			resultProgressBar.setVisibility(View.GONE);
			t.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		ListView listView = (ListView) adapter;
		HashMap<String, String> item = (HashMap<String, String>) listView.getAdapter().getItem(position);
		if (listView.getId() == R.id.qos_success_list) {
			activity.showExpandedResultDetail(results, DetailType.OK, Integer.valueOf(item.get("index")));
		}
		else if (listView.getId() == R.id.qos_error_list) {
			activity.showExpandedResultDetail(results, DetailType.FAIL, Integer.valueOf(item.get("index")));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		try {
			HashMap<String, String> item = (HashMap<String, String>) v.getTag();
			System.out.println("ON CLICK: " + item);
			activity.showExpandedResultDetail(results, DetailType.OK, Integer.valueOf(item.get("index")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
