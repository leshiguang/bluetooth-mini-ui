package com.lifesense.android.health.service.devicedetails.item.builder;

public enum ItemType {

    Switch {
        @Override
        public boolean showSwitch() {
            return true;
        }

        @Override
        public boolean showArrow() {
            return false;
        }
    },

    TextOnly {
        @Override
        public boolean showArrow() {
            return false;
        }
    },

    LinkText,
    Button {
        @Override
        public boolean showButton() {
            return true;
        }

        @Override
        public boolean showArrow() {
            return false;
        }

        @Override
        public boolean showTitle() {
            return false;
        }

        @Override
        public boolean showValue() {
            return false;
        }
    };


    public boolean showSwitch() {
        return false;
    }


    public boolean showArrow() {
        return true;
    }

    public boolean showButton() {
        return false;
    }

    public boolean showTag() {
        return false;
    }

    public boolean showTitle() {
        return true;
    }

    public boolean showValue() {
        return true;
    }
}
