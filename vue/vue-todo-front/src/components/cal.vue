<template>
  <div>
<!--    <datetime-picker :bind="time"></datetime-picker>-->
    <datetime-picker
     timeType="second"
     :value="time"
     @input="changeDate"
     min="2018-08-24 02:10:02"
     :timeStr="timeStr"
    />
  </div>
</template>

<script>
// then use it by need

export default {
  name: "cal",
  data: function () {
    return {
      timeStr: ['hour', 'min', 'sec'],
      time: '2019-02-01 01:02:01',
    }
  },
  methods: {
    changeDate: function (val) {
      this.time = val
      console.log(val)
    },
  },

  // components: { 'datetime-picker': VueDatepicker.DatetimePicker },
}
</script>

<style scoped>

</style>
