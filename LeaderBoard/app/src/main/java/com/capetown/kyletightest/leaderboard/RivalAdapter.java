package com.capetown.kyletightest.leaderboard;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RivalAdapter extends CursorAdapter {
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_LEADER = 1;

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView avatarView;
        public final TextView nameView;
        public final ImageView iconView;

        public ViewHolder(View view) {
            avatarView = (ImageView) view.findViewById(R.id.list_item_rival_avatar);
            nameView = (TextView) view.findViewById(R.id.list_item_rival_name);
            iconView = (ImageView) view.findViewById(R.id.list_item_battle_or_submit_icon);
        }
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_NORMAL: {
                layoutId = R.layout.list_item_rival;
                break;
            }
            case VIEW_TYPE_LEADER: {
                layoutId = R.layout.list_item_rival_leader;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    public RivalAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());

        // Read name from cursor
        String rivalName = cursor.getString(RivalFragment.COL_RIVAL_NAME);
        // Find TextView and set formatted date on it
        viewHolder.nameView.setText(rivalName);



    }
}
